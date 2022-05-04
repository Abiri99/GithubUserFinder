package com.example.githubuserfinder.user_detail.data.model

import com.example.githubuserfinder.core.data.getStringNullable
import org.json.JSONObject

class GithubUserDetailModel(
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
) {

    companion object {
        fun fromJson(json: JSONObject): GithubUserDetailModel {
            return GithubUserDetailModel(
                login = json.getString("login"),
                avatarUrl = json.getString("avatar_url"),
                following = json.getInt("following"),
                followers = json.getInt("followers"),
                publicRepos = json.getInt("public_repos"),
                name = json.getStringNullable("name"),
                company = json.getStringNullable("company"),
                blog = json.getStringNullable("blog"),
                location = json.getStringNullable("location"),
                email = json.getStringNullable("email"),
                twitterUsername = json.getStringNullable("twitter_username"),
                bio = json.getStringNullable("bio"),
            )
        }
    }
}
