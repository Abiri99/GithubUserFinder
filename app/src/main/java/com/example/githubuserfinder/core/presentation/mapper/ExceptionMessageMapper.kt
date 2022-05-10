package com.example.githubuserfinder.core.presentation.mapper

import com.example.githubuserfinder.core.presentation.CoreString
import com.example.githubuserfinder.core.presentation.util.Emoji
import java.io.IOException
import java.net.MalformedURLException

object ExceptionMessageMapper {

    fun getProperMessageForException(e: Exception): String {
        val message = when (e) {
            is IOException -> CoreString.IOExceptionMessage
            is MalformedURLException -> CoreString.MalformedURLExceptionMessage
            else -> CoreString.UnknownNetworkError
        }

        return message + " ${Emoji.womanFacePalming}${Emoji.manFacePalming}"
    }
}
