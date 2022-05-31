package com.example.core.data

/**
 * This is a custom exception type used in the [NetworkRequester] so that
 * the code wouldn't be dependant to a specific HTTP client exception type.
 * those kind of exceptions in the [NetworkRequester] will be caught and mapped to this type.
 * */
data class CustomNetworkException(
    override val message: String?
) : Exception()
