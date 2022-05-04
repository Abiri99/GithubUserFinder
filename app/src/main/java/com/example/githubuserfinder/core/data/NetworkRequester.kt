package com.example.githubuserfinder.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

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
    ): Result<T> =
        withContext(coroutineDispatcher) {
            // TODO: Find a solution for the below warning of the URL & openConnection()
            val apiUrl = URL(url)
            val urlConnection: HttpsURLConnection = apiUrl.openConnection() as HttpsURLConnection
            try {
                val responseStream: InputStream = BufferedInputStream(urlConnection.inputStream)
                val responseString = String(responseStream.readBytes(), StandardCharsets.UTF_8)
                val jsonResponse = JSONObject(responseString)

                Result.success(successResultMapper(jsonResponse))
            } catch (e: MalformedURLException) {
                // This exception could be throw when creating URL
                Result.failure(e)
            } catch (e: IOException) {
                // This exception could be thrown when calling `url.openConnection`
                Result.failure(e)
            } finally {
                urlConnection.disconnect()
            }
        }
}
