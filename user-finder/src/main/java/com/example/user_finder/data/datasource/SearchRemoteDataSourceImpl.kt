package com.example.user_finder.data.datasource

import com.example.user_finder.data.adapter.GithubSearchResponseJsonAdapter
import com.example.user_finder.data.model.GithubSearchResponseModel
import com.example.user_finder.presentation.UiString
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
            .invoke(url = URL(UiString.FetchUsersRoute(query)))

        return when (jsonResult) {
            is DataResult.Success -> {
                DataResult.Success(githubSearchResponseJsonAdapter.createEntityFromJson(jsonResult.value))
            }
            is DataResult.Error -> DataResult.Error(jsonResult.exception)
        }
    }
}