package com.example.githubuserfinder.user_finder.data.adapter

import com.example.githubuserfinder.core.data.JsonAdapter
import com.example.githubuserfinder.user_finder.data.model.GithubSearchItem
import org.json.JSONObject

class GithubSearchItemJsonAdapter : JsonAdapter<GithubSearchItem> {
    override fun createEntityFromJson(json: JSONObject): GithubSearchItem {
        return GithubSearchItem(
            id = json.getInt("id"),
            login = json.getString("login"),
            avatarUrl = json.getString("avatar_url"),
            score = json.getInt("score"),
        )
    }
}
