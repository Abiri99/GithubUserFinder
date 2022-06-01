package com.example.user_detail.data.datasource

import com.example.core.data.DataResult
import com.example.core.data.NetworkRequester
import com.example.user_detail.data.model.GithubUserDetail
import com.example.user_detail.presentation.UserDetailString
import com.example.user_detail.data.adapter.GithubUserDetailJsonAdapter
import java.net.URL

class UsersRemoteDataSourceImpl(
    private val networkRequester: NetworkRequester,
    private val githubUserDetailJsonAdapter: GithubUserDetailJsonAdapter,
) : UsersRemoteDataSource {

    /**
     * @see [UsersRemoteDataSource.getUser]
     * */
    override suspend fun getUser(username: String): DataResult<GithubUserDetail> {
        val jsonResult =
            networkRequester.invoke(url = URL(UserDetailString.GetUserApiRoute(username)))

        return when (jsonResult) {
            is DataResult.Success -> {
                DataResult.Success(githubUserDetailJsonAdapter.createEntityFromJson(jsonResult.value))
            }
            is DataResult.Error -> DataResult.Error(exception = jsonResult.exception)
        }
    }
}