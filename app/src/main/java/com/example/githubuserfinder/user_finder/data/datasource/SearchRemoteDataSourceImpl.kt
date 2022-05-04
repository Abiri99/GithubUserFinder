package com.example.githubuserfinder.user_finder.data.datasource

import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.core.data.NetworkRequester
import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponse

class SearchRemoteDataSourceImpl(
    private val networkRequester: NetworkRequester,
) : SearchRemoteDataSource {

    // TODO: Handle exceptions
    override suspend fun fetchUsers(query: String): DataResult<GithubSearchResponse> = networkRequester
        .invoke(
            url = "https://api.github.com/search/users?q=$query",
            successResultMapper = { json ->
                GithubSearchResponse.fromJson(json)
            },
        )
}
