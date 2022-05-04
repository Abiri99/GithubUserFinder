package com.example.githubuserfinder.user_finder.data.model

import org.json.JSONObject

class GithubSearchResponse private constructor(
    private val totalCount: Int,
    private val incompleteResults: Boolean,
    private val items: List<GithubSearchItem>,
) {

    companion object {
        fun fromJson(json: JSONObject): GithubSearchResponse {
            val items = json.getJSONArray("items")
            val parsedItems: MutableList<GithubSearchItem> = mutableListOf()

            for (i in 0 until items.length()) {
                parsedItems.add(GithubSearchItem.fromJson(items.getJSONObject(i)))
            }

            return GithubSearchResponse(
                totalCount = json.getInt("total_count"),
                incompleteResults = json.getBoolean("incomplete_results"),
                items = parsedItems,
            )
        }
    }

    override fun toString(): String {
        return "\n[\n\ttotalCount=$totalCount,\n\tincompleteResults=$incompleteResults,\n\titems=$items\n]"
    }
}
