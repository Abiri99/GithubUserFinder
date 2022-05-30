package com.example.user_finder.data.adapter

import com.example.core_android.data.JsonAdapter
import com.example.user_finder.data.model.GithubSearchItemModel
import com.example.user_finder.presentation.UiString
import org.json.JSONObject

/**
 * This class will handle converting a [JSONObject] to a [GithubSearchItemModel] model
 * */
internal class GithubSearchItemJsonAdapter : JsonAdapter<GithubSearchItemModel> {
    override fun createEntityFromJson(json: JSONObject): GithubSearchItemModel {
        return GithubSearchItemModel(
            id = json.getInt(UiString.GithubSearchItemIdKey),
            login = json.getString(UiString.GithubSearchItemLoginKey),
            avatarUrl = json.getString(UiString.GithubSearchItemAvatarUrlKey),
            score = json.getInt(UiString.GithubSearchItemScoreKey),
        )
    }
}
