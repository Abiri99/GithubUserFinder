package com.example.core.presentation

import androidx.compose.ui.Modifier
import com.example.core.BuildConfig

/**
 * This extension function will set a modifier only
 * when the application is running in Debug mode
 * */
fun Modifier.debugModifier(modifier: Modifier): Modifier {
    return this.then(
        if (BuildConfig.DEBUG) {
            modifier
        } else {
            Modifier
        }
    )
}
