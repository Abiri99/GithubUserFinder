package com.example.githubuserfinder.core.data

import android.util.Log
import com.example.githubuserfinder.BuildConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

private const val TAG = "NetworkRequester"

/**
 * This class:
 *  - Ensures that every connection is secured using HTTPS protocol
 *  - Handles exceptions
 *  - Returns a [Result]
 *
 *  @param successResultMapper is a lambda to convert JSONObject into a custom Kotlin class
 * */
class NetworkRequester {

    suspend fun <T> invoke(
        url: String,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        successResultMapper: (JSONObject) -> T,
    ): Result<T>? =
        withContext(coroutineDispatcher) {
            lateinit var urlConnection: HttpsURLConnection
            try {
                // TODO: Find a solution for the below warning of the URL & openConnection()
                val apiUrl = URL(url)
                ensureActive()
                urlConnection = apiUrl.openConnection() as HttpsURLConnection
                ensureActive()
                when (urlConnection.responseCode) {
                    200 -> {
                        val responseStream: InputStream =
                            BufferedInputStream(urlConnection.inputStream)
                        ensureActive()
                        val responseString =
                            String(responseStream.readBytes(), StandardCharsets.UTF_8)
                        ensureActive()
                        val jsonResponse = JSONObject(responseString)

                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "server response: $jsonResponse")
                        }

                        Result.success(successResultMapper(jsonResponse))
                    }
                    304 -> {
                        Result.failure(CustomNetworkException("Not modified!"))
                    }
                    404 -> {
                        Result.failure(CustomNetworkException("Resource not found!"))
                    }
                    422 -> {
                        Result.failure(CustomNetworkException("Validation failed!"))
                    }
                    503 -> {
                        Result.failure(CustomNetworkException("Service unavailable!"))
                    }
                    else -> {
                        Result.failure(CustomNetworkException("Unknown network exception!"))
                    }
                }
            } catch (e: MalformedURLException) {
                // This exception could be throw when creating URL
                Result.failure(e)
            } catch (e: IOException) {
                // This exception could be thrown when calling `url.openConnection`
                Result.failure(e)
            } catch (e: CancellationException) {
                // This exception could be throw when the coroutine is cancelled
                null
            } finally {
                urlConnection.disconnect()
            }
        }
}
