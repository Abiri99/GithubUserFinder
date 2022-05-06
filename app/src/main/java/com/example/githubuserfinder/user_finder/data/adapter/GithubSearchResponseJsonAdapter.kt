package com.example.githubuserfinder.user_finder.data.adapter

import com.example.githubuserfinder.core.data.JsonAdapter
import com.example.githubuserfinder.user_finder.data.model.GithubSearchItem
import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponse
import org.json.JSONObject

class GithubSearchResponseJsonAdapter(
    private val githubSearchItemJsonAdapter: GithubSearchItemJsonAdapter,
) : JsonAdapter<GithubSearchResponse> {
    override fun createEntityFromJson(json: JSONObject): GithubSearchResponse {
        val items = json.getJSONArray("items")
        val parsedItems: MutableList<GithubSearchItem> = mutableListOf()

        for (i in 0 until items.length()) {
            parsedItems.add(githubSearchItemJsonAdapter.createEntityFromJson(items.getJSONObject(i)))
        }

        return GithubSearchResponse(
            totalCount = json.getInt("total_count"),
            incompleteResults = json.getBoolean("incomplete_results"),
            items = parsedItems,
        )
    }
}
