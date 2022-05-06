package com.example.githubuserfinder.user_finder.data.model

data class GithubSearchResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<GithubSearchItem>,
)
