package com.example.user_finder.data.model

internal data class GithubSearchResponseModel(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val itemModels: List<GithubSearchItemModel>,
)
