package com.example.user_finder.data.adapter

import com.example.core.data.JsonAdapter
import com.example.user_finder.data.model.GithubSearchItemModel
import com.example.user_finder.data.model.GithubSearchResponseModel
import org.json.JSONObject

/**
 * This class will handle converting a [JSONObject] to a [GithubSearchResponseModel] model
 * */
class GithubSearchResponseJsonAdapter(
    private val githubSearchItemJsonAdapter: GithubSearchItemJsonAdapter,
) : JsonAdapter<GithubSearchResponseModel> {
    override fun createEntityFromJson(json: JSONObject): GithubSearchResponseModel {
        val items = json.getJSONArray("items")
        val parsedItemModels: MutableList<GithubSearchItemModel> = mutableListOf()

        for (i in 0 until items.length()) {
            parsedItemModels.add(githubSearchItemJsonAdapter.createEntityFromJson(items.getJSONObject(i)))
        }

        return GithubSearchResponseModel(
            totalCount = json.getInt("total_count"),
            incompleteResults = json.getBoolean("incomplete_results"),
            itemModels = parsedItemModels,
        )
    }
}
