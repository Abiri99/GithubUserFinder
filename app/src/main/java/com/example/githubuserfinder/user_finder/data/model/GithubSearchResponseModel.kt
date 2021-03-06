package com.example.githubuserfinder.user_finder.data.model

data class GithubSearchResponseModel(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val itemModels: List<GithubSearchItemModel>,
)
