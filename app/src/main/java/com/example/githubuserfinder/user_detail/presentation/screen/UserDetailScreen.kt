package com.example.githubuserfinder.user_detail.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.githubuserfinder.core.presentation.CustomTextStyle
import com.example.githubuserfinder.core.presentation.Emoji
import com.example.githubuserfinder.core.presentation.component.NetworkImage
import com.example.githubuserfinder.core.presentation.component.TouchableScale
import com.example.githubuserfinder.core.presentation.rememberStateWithLifecycle
import com.example.githubuserfinder.user_detail.presentation.component.UserDetailHeader
import com.example.githubuserfinder.user_detail.presentation.viewmodel.UserDetailViewModel

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel,
    navController: NavController,
    username: String,
) {

    // This is supposed the be run only once
    LaunchedEffect(true) {
        viewModel.fetchUserData(username = username)
    }

    val uiState = rememberStateWithLifecycle(stateFlow = viewModel.uiState)

    val configuration = LocalConfiguration.current

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        UserDetailHeader(
            username = username,
            onNavigatedBack = { navController.navigateUp() },
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            when {
                uiState.value.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .requiredSize(48.dp)
                            .align(Alignment.Center),
                        color = Color.Black,
                        strokeWidth = 4.dp,
                    )
                }
                uiState.value.userData?.isSuccess == true -> {
                    val data = uiState.value.userData!!.getOrNull()!!

                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        NetworkImage(
                            url = data.avatarUrl,
                            modifier = Modifier
                                .requiredWidth(
                                    configuration.screenWidthDp.dp
                                )
                                .requiredHeight(
                                    configuration.screenWidthDp.dp
                                )
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawContent()

                                        val gradient = Brush.verticalGradient(
                                            colors = listOf(Color.Black, Color.Transparent),
                                            startY = size.height,
                                            endY = 3 * size.height / 4,
                                        )
                                        drawRect(gradient)
                                    }
                                },
                            imageModifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
                uiState.value.userData?.isFailure == true -> {
                    TouchableScale(onClick = {
                        viewModel.fetchUserData(username = username)
                    }) {
                        val errorMessage =
                            if (uiState.value.userData?.exceptionOrNull() != null && uiState.value.userData?.exceptionOrNull()?.message?.isBlank() == false) {
                                uiState.value.userData!!.exceptionOrNull()!!.message!!
                            } else {
                                Emoji.womanFacePalming + Emoji.manFacePalming + "\n" + "Failed to fetch data, tap to " + Emoji.refresh
                            }
                        Text(
                            text = errorMessage,
                            style = CustomTextStyle.contentLargeSemiBold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 24.dp),
                        )
                    }
                }
                uiState.value.userData == null -> {
                    // In case the request is cancelled
                }
            }
        }
    }
}
