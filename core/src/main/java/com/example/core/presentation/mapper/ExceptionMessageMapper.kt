package com.example.core.presentation.mapper

import com.example.core.presentation.CoreString
import com.example.core.presentation.util.Emoji
import java.io.IOException
import java.net.MalformedURLException

/**
 * This object is used to map data layer exception messages to a
 * proper message which could be shown to the user in the presentation layer.
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
