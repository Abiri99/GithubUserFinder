package com.example.user_finder.data.datasource

import com.example.core.data.DataResult
import com.example.user_finder.data.model.GithubSearchResponseModel

interface SearchRemoteDataSource {

    /**
     * This method fetches a list of the users whom name contains the [query]
     * */
    suspend fun fetchUsers(query: String): DataResult<GithubSearchResponseModel>
}
