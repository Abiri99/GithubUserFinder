package com.example.githubuserfinder.user_finder.data.datasource

import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.core.data.NetworkRequester
import com.example.githubuserfinder.user_finder.data.adapter.GithubSearchResponseJsonAdapter
import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponseModel
import com.example.githubuserfinder.user_finder.presentation.UserFinderString
import java.net.URL

class SearchRemoteDataSourceImpl(
    private val networkRequester: NetworkRequester,
    private val githubSearchResponseJsonAdapter: GithubSearchResponseJsonAdapter,
) : SearchRemoteDataSource {

    /**
     * @see [SearchRemoteDataSource.fetchUsers]
     * */
    override suspend fun fetchUsers(query: String): DataResult<GithubSearchResponseModel> {
        val jsonResult = networkRequester
            .invoke(url = URL(UserFinderString.FetchUsersRoute(query)))

        return when (jsonResult) {
            is DataResult.Success -> {
                DataResult.Success(githubSearchResponseJsonAdapter.createEntityFromJson(jsonResult.value))
            }
            is DataResult.Error -> DataResult.Error(jsonResult.exception)
        }
    }
}
