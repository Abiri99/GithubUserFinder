package com.example.githubuserfinder.core

import androidx.compose.ui.Modifier
import com.example.githubuserfinder.BuildConfig

fun Modifier.debugModifier(modifier: Modifier): Modifier {

    this.then(
        if (BuildConfig.DEBUG) {
            modifier
        } else {
            Modifier
        }
    )

    return this
}
