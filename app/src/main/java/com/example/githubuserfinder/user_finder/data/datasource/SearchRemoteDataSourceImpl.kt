package com.example.githubuserfinder.user_finder.data.datasource

import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.core.data.NetworkRequester
import com.example.githubuserfinder.user_finder.data.adapter.GithubSearchResponseJsonAdapter
import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponse

class SearchRemoteDataSourceImpl(
    private val networkRequester: NetworkRequester,
    private val githubSearchResponseJsonAdapter: GithubSearchResponseJsonAdapter,
) : SearchRemoteDataSource {

    // TODO: Handle exceptions
    override suspend fun fetchUsers(query: String): DataResult<GithubSearchResponse> =
        networkRequester
            .invoke(
                url = "https://api.github.com/search/users?q=$query",
                successResultMapper = { json ->
                    githubSearchResponseJsonAdapter.createEntityFromJson(json)
                },
            )
}
