package com.example.user_detail.data.model

/**
 * This model contains user's profile data.
 * */
data class GithubUserDetail(
    val login: String,
    val avatarUrl: String,
    val following: Int,
    val followers: Int,
    val publicRepos: Int,
    val name: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val twitterUsername: String? = null,
    val bio: String? = null,
)
