package com.example.core.presentation

import io.michaelrocks.paranoid.Obfuscate

/**
 * These are all of the strings used in the core layer and all of them are obfuscated.
 * */
@Obfuscate
object CoreString {

    const val IOExceptionMessage = "Internet error!"
    const val MalformedURLExceptionMessage = "Malformed url!\nPlease contact the service provider"
    const val UnknownNetworkError = "Unknown error!"

    const val HTTP_304_ERROR_MESSAGE = "Not modified!"
    const val HTTP_404_ERROR_MESSAGE = "Resource not found!"
    const val HTTP_422_ERROR_MESSAGE = "Validation failed!"
    const val HTTP_503_ERROR_MESSAGE = "Service unavailable!"
    const val HTTP_UNKNOWN_ERROR_MESSAGE = "Unknown network error!"
}
