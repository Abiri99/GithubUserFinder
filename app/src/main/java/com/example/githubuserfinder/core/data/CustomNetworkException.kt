package com.example.githubuserfinder.core.data

data class CustomNetworkException(
    override val message: String?
) : Exception()
