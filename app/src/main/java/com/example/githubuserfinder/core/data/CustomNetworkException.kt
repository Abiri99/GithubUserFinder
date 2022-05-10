package com.example.githubuserfinder.core.data

/**
 * This is a custom exception type used in the [NetworkRequester] so that
 * we wouldn't be dependant to a HTTP client exception types. We will catch
 * those kind of exceptions in the [NetworkRequester] and map them
 * to this type.
 * */
data class CustomNetworkException(
    override val message: String?
) : Exception()
