package com.example.githubuserfinder.user_detail.data.datasource

import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.core.data.NetworkRequester
import com.example.githubuserfinder.user_detail.data.adapter.GithubUserDetailJsonAdapter
import com.example.githubuserfinder.user_detail.data.model.GithubUserDetail
import java.net.URL

class UsersRemoteDataSourceImpl(
    private val networkRequester: NetworkRequester,
    private val githubUserDetailJsonAdapter: GithubUserDetailJsonAdapter,
) : UsersRemoteDataSource {

    override suspend fun getUser(username: String): DataResult<GithubUserDetail> =
        networkRequester.invoke(
            url = URL("https://api.github.com/users/$username"),
            successResultMapper = { json ->
                githubUserDetailJsonAdapter.createEntityFromJson(json)
            },
        )
}
