package com.example.githubuserfinder.core.data

import com.example.githubuserfinder.core.presentation.CoreString.HTTP_304_ERROR_MESSAGE
import com.example.githubuserfinder.core.presentation.CoreString.HTTP_404_ERROR_MESSAGE
import com.example.githubuserfinder.core.presentation.CoreString.HTTP_422_ERROR_MESSAGE
import com.example.githubuserfinder.core.presentation.CoreString.HTTP_503_ERROR_MESSAGE
import com.example.githubuserfinder.core.presentation.CoreString.HTTP_UNKNOWN_ERROR_MESSAGE
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

@RunWith(RobolectricTestRunner::class)
class NetworkRequesterTest {

    private lateinit var mockUrl: URL

    private lateinit var mockConnection: HttpsURLConnection

    private lateinit var sut: NetworkRequester

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        mockUrl = mock()
        mockConnection = mock()
        sut = NetworkRequester()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun whenStatusCode200_thenReturnResponse(): Unit = runBlocking {
        whenever(mockUrl.openConnection()).thenReturn(mockConnection)
        whenever(mockConnection.responseCode).thenReturn(200)

        val fakeResponse = """
            {
                "id": 123
            }
        """.trimIndent()
        val fakeResponseStream: InputStream = ByteArrayInputStream(fakeResponse.toByteArray())
        whenever(mockConnection.inputStream).thenReturn(fakeResponseStream)

        val fakeModelJsonAdapter = FakeApiSuccessResponseJsonAdapter()

        val result = sut.invoke(
            mockUrl,
        )

        val responseModel = fakeModelJsonAdapter.createEntityFromJson(result.value!!)

        assertThat(responseModel).isEqualTo(
            FakeApiSuccessResponse(id = 123)
        )
    }

    @Test
    fun whenStatusCodeNot200_thenReturnError(): Unit = runBlocking {
        whenever(mockUrl.openConnection()).thenReturn(mockConnection)

        val responseHttpCode = (300..600).random()
        whenever(mockConnection.responseCode).thenReturn(responseHttpCode)

        val result = sut.invoke(mockUrl)

        assertThat(result.exception).isInstanceOf(CustomNetworkException::class.java)

        // This condition shall be divided into multiple tests
        when (responseHttpCode) {
            304 -> assertThat(result.exception?.message).isEqualTo(HTTP_304_ERROR_MESSAGE)
            404 -> assertThat(result.exception?.message).isEqualTo(HTTP_404_ERROR_MESSAGE)
            422 -> assertThat(result.exception?.message).isEqualTo(HTTP_422_ERROR_MESSAGE)
            503 -> assertThat(result.exception?.message).isEqualTo(HTTP_503_ERROR_MESSAGE)
            else -> assertThat(result.exception?.message).isEqualTo(HTTP_UNKNOWN_ERROR_MESSAGE)
        }
    }

    @Test
    fun whenMalformedUrlExceptionThrown_thenCatchAndReturnProperError(): Unit = runBlocking {

        val malformedUrlException = MalformedURLException("Malformed url!")

        whenever(mockUrl.openConnection()).thenThrow(malformedUrlException)

        val result = sut.invoke(
            mockUrl,
        )

        assertThat(result.exception).isInstanceOf(CustomNetworkException::class.java)
        assertThat(result.exception?.message).isEqualTo(malformedUrlException.message)
    }

    @Test
    fun whenIOExceptionThrown_thenCatchAndReturnProperError(): Unit = runBlocking {

        val ioException = IOException("IO Exception!")

        whenever(mockUrl.openConnection()).thenThrow(ioException)

        val result = sut.invoke(
            mockUrl,
        )

        assertThat(result.exception).isInstanceOf(CustomNetworkException::class.java)
        assertThat(result.exception?.message).isEqualTo(ioException.message)
    }
}

private data class FakeApiSuccessResponse(
    val id: Int,
)

private class FakeApiSuccessResponseJsonAdapter : JsonAdapter<FakeApiSuccessResponse> {
    override fun createEntityFromJson(json: JSONObject): FakeApiSuccessResponse {
        return FakeApiSuccessResponse(
            id = json.getInt("id")
        )
    }
}
