package com.example.githubuserfinder.core.data

import org.json.JSONObject

fun JSONObject.getStringNullable(name: String): String? {
    return if (this.has(name)) {
        this.getString(name)
    } else {
        null
    }
}

fun JSONObject.getIntNullable(name: String): Int? {
    return if (this.has(name)) {
        this.getInt(name)
    } else {
        null
    }
}
