package com.example.githubuserfinder.core.presentation.mapper

import com.example.githubuserfinder.core.presentation.CoreString
import com.example.githubuserfinder.core.presentation.util.Emoji
import java.io.IOException
import java.net.MalformedURLException

/**
 * This class is used to map data layer exception messages to a
 * proper message which we can show to the user
 * */
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
