package com.example.githubuserfinder.core.data

import org.json.JSONObject

interface JsonAdapter<T> {

    fun createEntityFromJson(json: JSONObject): T
}
