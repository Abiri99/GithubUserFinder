package com.example.githubuserfinder.core.data

import org.json.JSONObject

/**
 * This interface must be implemented for any network response entity
 * so that the [JSONObject] could be converted to the entity in form of a Kotlin object.
 * */
interface JsonAdapter<T> {

    fun createEntityFromJson(json: JSONObject): T
}
