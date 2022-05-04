package com.example.githubuserfinder.user_finder.data.datasource

import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

class SearchRemoteDataSourceImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : SearchRemoteDataSource {

    // TODO: Handle exceptions
    override suspend fun fetchUsers(query: String): Result<GithubSearchResponse> =
        withContext(ioDispatcher) {
            val url = URL("https://api.github.com/search/users?q=j")
            val urlConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
            try {
                val responseStream: InputStream = BufferedInputStream(urlConnection.inputStream)
                val responseString = String(responseStream.readBytes(), StandardCharsets.UTF_8)
                val jsonResponse = JSONObject(responseString)

                Result.success(GithubSearchResponse.fromJson(jsonResponse))
            } finally {
                urlConnection.disconnect()
            }
        }
}
