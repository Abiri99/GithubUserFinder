package com.example.githubuserfinder.core.presentation.mapper

import com.example.githubuserfinder.core.presentation.util.Emoji
import java.io.IOException
import java.net.MalformedURLException

object ExceptionMessageMapper {

    fun getProperMessageForException(e: Exception): String {
        val message = when (e) {
            is IOException -> "Internet error!"
            is MalformedURLException -> "Malformed url!\nPlease contact the service provider"
            else -> "Unknown Error!"
        }

        return message + " ${Emoji.womanFacePalming}${Emoji.manFacePalming}"
    }
}
