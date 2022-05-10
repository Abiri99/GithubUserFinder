package com.example.githubuserfinder.user_finder.data.adapter

import com.example.githubuserfinder.core.data.JsonAdapter
import com.example.githubuserfinder.user_finder.data.model.GithubSearchItem
import com.example.githubuserfinder.user_finder.presentation.UserFinderString
import org.json.JSONObject

/**
 * This class will handle converting a [JSONObject] to a [GithubSearchItem] model
 * */
class GithubSearchItemJsonAdapter : JsonAdapter<GithubSearchItem> {
    override fun createEntityFromJson(json: JSONObject): GithubSearchItem {
        return GithubSearchItem(
            id = json.getInt(UserFinderString.GithubSearchItemIdKey),
            login = json.getString(UserFinderString.GithubSearchItemLoginKey),
            avatarUrl = json.getString(UserFinderString.GithubSearchItemAvatarUrlKey),
            score = json.getInt(UserFinderString.GithubSearchItemScoreKey),
        )
    }
}
