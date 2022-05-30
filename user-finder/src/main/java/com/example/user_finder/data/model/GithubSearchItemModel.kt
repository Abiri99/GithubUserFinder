package com.example.user_finder.data.model

internal data class GithubSearchItemModel(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val score: Int,
)
