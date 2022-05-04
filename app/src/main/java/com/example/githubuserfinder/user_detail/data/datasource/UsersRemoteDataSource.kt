package com.example.githubuserfinder.user_detail.data.datasource

import com.example.githubuserfinder.user_detail.data.model.GithubUserDetailModel

interface UsersRemoteDataSource {

    suspend fun getUser(username: String): Result<GithubUserDetailModel>?
}
