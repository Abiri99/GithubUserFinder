package com.example.githubuserfinder.user_finder.data.datasource

import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.core.data.NetworkRequester
import com.example.githubuserfinder.user_finder.data.adapter.GithubSearchResponseJsonAdapter
import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponse
import com.example.githubuserfinder.user_finder.presentation.UserFinderString
import java.net.URL

class SearchRemoteDataSourceImpl(
    private val networkRequester: NetworkRequester,
    private val githubSearchResponseJsonAdapter: GithubSearchResponseJsonAdapter,
) : SearchRemoteDataSource {

    // TODO: Handle exceptions
    override suspend fun fetchUsers(query: String): DataResult<GithubSearchResponse> {
//        withContext(Dispatchers.IO) {
//            val okHttpClient = OkHttpClient.Builder().build()
//            val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
//                .client(okHttpClient)
//                .addConverterFactory(
//                    MoshiConverterFactory.create(
//                        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//                    )
//                ).build()
//            val searchApi = retrofit.create(SearchApi::class.java)
//            val response = searchApi.fetchUsers(query)
//            response.enqueue(object : Callback<GithubSearchResponseModel> {
//                override fun onFailure(call: Call<GithubSearchResponseModel>, t: Throwable) {
//                    Log.d("DS", t.message.toString())
//                }
//
//                override fun onResponse(
//                    call: Call<GithubSearchResponseModel>,
//                    response: Response<GithubSearchResponseModel>
//                ) {
//                    Log.d("DS", response.body().toString())
//                }
//            })
//            CertificateFA
//            response.execute()
//        response.enqueue()
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
