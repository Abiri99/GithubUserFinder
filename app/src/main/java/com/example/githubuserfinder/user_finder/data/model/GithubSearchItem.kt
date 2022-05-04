package com.example.githubuserfinder.user_finder.data.model

import android.net.Uri
import androidx.core.net.toUri
import org.json.JSONObject

class GithubSearchItem private constructor(
    private val id: Int,
    private val login: String,
    private val avatarUri: Uri,
    private val score: Int,
) {

    companion object {
        fun fromJson(json: JSONObject): GithubSearchItem {
            return GithubSearchItem(
                id = json.getInt("id"),
                login = json.getString("login"),
                avatarUri = json.getString("avatar_url").toUri(),
                score = json.getInt("score"),
            )
        }
    }

    override fun toString(): String {
        return "\n[\n\tid=$id,\n\tlogin=$login,\n\tavatarUri=$avatarUri,\n\tscore=$score\n]"
    }
}
