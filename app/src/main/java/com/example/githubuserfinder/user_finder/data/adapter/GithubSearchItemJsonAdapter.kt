package com.example.githubuserfinder.user_finder.data.adapter

import com.example.core.data.JsonAdapter
import com.example.githubuserfinder.user_finder.data.model.GithubSearchItemModel
import com.example.githubuserfinder.user_finder.presentation.UserFinderString
import org.json.JSONObject

/**
 * This class will handle converting a [JSONObject] to a [GithubSearchItemModel] model
 * */
class GithubSearchItemJsonAdapter : JsonAdapter<GithubSearchItemModel> {
    override fun createEntityFromJson(json: JSONObject): GithubSearchItemModel {
        return GithubSearchItemModel(
            id = json.getInt(UserFinderString.GithubSearchItemIdKey),
            login = json.getString(UserFinderString.GithubSearchItemLoginKey),
            avatarUrl = json.getString(UserFinderString.GithubSearchItemAvatarUrlKey),
            score = json.getInt(UserFinderString.GithubSearchItemScoreKey),
        )
    }
}
