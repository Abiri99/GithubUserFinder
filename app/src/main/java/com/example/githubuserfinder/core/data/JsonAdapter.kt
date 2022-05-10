package com.example.githubuserfinder.core.data

import org.json.JSONObject

/**
 * This interface must be implemented for any network entity so that we can
 * convert the [JSONObject] to the entity in form of a Kotlin object
 * */
interface JsonAdapter<T> {

    fun createEntityFromJson(json: JSONObject): T
}
