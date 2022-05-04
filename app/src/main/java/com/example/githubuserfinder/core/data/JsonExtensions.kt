package com.example.githubuserfinder.core.data

import org.json.JSONObject

fun JSONObject.getStringNullable(name: String): String? {
    if (!this.has(name)) return null

    if (this.getString(name) == "null") return null

    return this.getString(name)
}
