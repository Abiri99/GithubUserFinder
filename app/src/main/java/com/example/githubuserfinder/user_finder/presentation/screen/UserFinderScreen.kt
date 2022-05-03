package com.example.githubuserfinder.user_finder.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.githubuserfinder.core.debugModifier
import com.example.githubuserfinder.user_finder.presentation.component.UserFinderAppBar

@Composable
fun UserFinderScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .debugModifier(Modifier.background(Color.Green.copy(0.1f))),
    ) {
        // App Bar
        UserFinderAppBar()

        // Content
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
        }
    }
}
