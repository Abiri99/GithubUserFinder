package com.example.githubuserfinder.user_finder.data.model

import org.json.JSONObject

class GithubSearchItem constructor(
    val id: Int,
    val login: String,
    val avatarUri: String,
    val score: Int,
) {

    companion object {
        fun fromJson(json: JSONObject): GithubSearchItem {
            return GithubSearchItem(
                id = json.getInt("id"),
                login = json.getString("login"),
                avatarUri = json.getString("avatar_url"),
                score = json.getInt("score"),
            )
        }
    }

    override fun toString(): String {
        return "\n[\n\tid=$id,\n\tlogin=$login,\n\tavatarUri=$avatarUri,\n\tscore=$score\n]"
    }
}
