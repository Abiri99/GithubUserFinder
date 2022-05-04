package com.example.githubuserfinder.user_finder.data.datasource

import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponse

interface SearchRemoteDataSource {

    suspend fun fetchUsers(query: String): Result<GithubSearchResponse>
}
