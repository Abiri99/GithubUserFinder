package com.example.githubuserfinder.user_detail.data.datasource

import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.core.data.NetworkRequester
import com.example.githubuserfinder.user_detail.data.adapter.GithubUserDetailJsonAdapter
import com.example.githubuserfinder.user_detail.data.model.GithubUserDetail
import com.example.githubuserfinder.user_detail.presentation.UserDetailString
import java.net.URL
import javax.inject.Inject

class UsersRemoteDataSourceImpl @Inject constructor(
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
