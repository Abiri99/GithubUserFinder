package com.example.githubuserfinder.core.presentation

import androidx.compose.ui.Modifier
import com.example.githubuserfinder.BuildConfig

const val ENABLE_DEBUG_MODIFIER = false

/**
 * This extension function will set a modifier only
 * when the application is running in Debug mode
 * */
fun Modifier.debugModifier(modifier: Modifier): Modifier {
    return this.then(
        if (BuildConfig.DEBUG && ENABLE_DEBUG_MODIFIER) {
            modifier
        } else {
            Modifier
        }
    )
}
