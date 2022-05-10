package com.example.githubuserfinder.user_finder.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/users")
    fun fetchUsers(@Query(value = "q") query: String): Call<GithubSearchResponseModel>
}

data class GithubSearchResponseModel(
    @Json(name = "total_count")
    val total_count: Int,
    @Json(name = "incomplete_results")
    val incomplete_results: Boolean,
)
