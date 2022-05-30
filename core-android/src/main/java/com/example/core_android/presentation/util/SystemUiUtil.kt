package com.example.core_android.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object SystemUiUtil {

    /**
     * This method will set the status bar and it's icon color.
     * It probably needs to be called this method for every [NavigationDestination]
     * */
    @Composable
    fun ConfigStatusBar(color: Color, useDarkIcons: Boolean = false) {
        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setStatusBarColor(
                color = color,
                darkIcons = useDarkIcons,
            )
        }
    }
}