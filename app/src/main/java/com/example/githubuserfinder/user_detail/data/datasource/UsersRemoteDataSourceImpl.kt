package com.example.githubuserfinder.user_detail.data.datasource

import com.example.githubuserfinder.core.data.NetworkRequester
import com.example.githubuserfinder.user_detail.data.model.GithubUserDetailModel

class UsersRemoteDataSourceImpl(
    private val networkRequester: NetworkRequester,
) : UsersRemoteDataSource {

    override suspend fun getUser(username: String): Result<GithubUserDetailModel>? =
        networkRequester.invoke(
            url = "https://api.github.com/users/$username",
            successResultMapper = { json ->
                GithubUserDetailModel.fromJson(json)
            },
        )
}
