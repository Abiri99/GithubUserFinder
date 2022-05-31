package com.example.githubuserfinder.user_detail.data.datasource

import com.example.core.data.DataResult
import com.example.githubuserfinder.user_detail.data.model.GithubUserDetail

interface UsersRemoteDataSource {

    /**
     * This method sends a username to the server
     * and fetches user profile data
     * */
    suspend fun getUser(username: String): DataResult<GithubUserDetail>
}
