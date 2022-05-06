package com.example.githubuserfinder.user_detail.data.adapter

import com.example.githubuserfinder.core.data.JsonAdapter
import com.example.githubuserfinder.core.data.getStringNullable
import com.example.githubuserfinder.user_detail.data.model.GithubUserDetail
import org.json.JSONObject

class GithubUserDetailJsonAdapter : JsonAdapter<GithubUserDetail> {
    override fun createEntityFromJson(json: JSONObject): GithubUserDetail {
        return GithubUserDetail(
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
