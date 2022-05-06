package com.example.githubuserfinder.user_detail.data.datasource

import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.user_detail.data.model.GithubUserDetail

interface UsersRemoteDataSource {

    suspend fun getUser(username: String): DataResult<GithubUserDetail>
}
