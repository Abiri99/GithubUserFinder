package com.example.githubuserfinder.user_detail.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.githubuserfinder.user_detail.presentation.component.UserDetailHeader

@Composable
fun UserDetailScreen(
    navController: NavController,
    username: String,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        UserDetailHeader(
            username = username,
            onNavigatedBack = { navController.navigateUp() },
        )
    }
}
