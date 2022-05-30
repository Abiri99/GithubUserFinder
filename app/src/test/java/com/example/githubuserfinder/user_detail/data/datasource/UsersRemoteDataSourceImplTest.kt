package com.example.githubuserfinder.user_detail.data.datasource

import com.example.githubuserfinder.core.data.NetworkRequester
import com.example.githubuserfinder.user_detail.data.adapter.GithubUserDetailJsonAdapter
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verifyBlocking
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import java.lang.Exception
import java.net.URL

@RunWith(RobolectricTestRunner::class)
class UsersRemoteDataSourceImplTest {

    private lateinit var networkRequester: NetworkRequester

    private lateinit var githubUserDetailJsonAdapter: GithubUserDetailJsonAdapter

    private lateinit var sut: UsersRemoteDataSource

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        networkRequester = mock()
        githubUserDetailJsonAdapter = mock()
        sut = UsersRemoteDataSourceImpl(networkRequester, githubUserDetailJsonAdapter)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun whenGetUser_thenReturnNetworkRequestResponse(): Unit = runBlocking {

        val username = "JakeWharton"
        val url = "https://api.github.com/users/$username"

        val exception = Exception("test")
        val networkRequestResult = DataResult.Error(exception)

        whenever(networkRequester.invoke(URL(url))).thenReturn(networkRequestResult)

        val result = sut.getUser(username)

        assertThat(result.exception).isEqualTo(exception)
    }

    @Test
    fun whenGetUser_thenVerifyNetworkRequesterWorking(): Unit = runBlocking {

        val username = "JakeWharton"
        val url = "https://api.github.com/users/$username"

        whenever(networkRequester.invoke(URL(url))).thenReturn(DataResult.Error(Exception("test")))

        sut.getUser(username)

        verifyBlocking(networkRequester, times(1)) {
            invoke(url = URL(url))
        }
    }
}
