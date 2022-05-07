package com.example.githubuserfinder.user_detail.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.core.presentation.CustomTextStyle
import com.example.githubuserfinder.core.presentation.Emoji
import com.example.githubuserfinder.core.presentation.component.NetworkImage
import com.example.githubuserfinder.core.presentation.component.TouchableScale
import com.example.githubuserfinder.core.presentation.rememberStateWithLifecycle
import com.example.githubuserfinder.user_detail.presentation.component.UserDetailHeader
import com.example.githubuserfinder.user_detail.presentation.component.UserDetailTableRow
import com.example.githubuserfinder.user_detail.presentation.viewmodel.UserDetailViewModel

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel,
    username: String,
    onNavigateBack: () -> Unit,
) {

    BackHandler {
        viewModel.cancelFetchingData()
    }

    // This is supposed the be run only once
    LaunchedEffect(true) {
        viewModel.fetchUserData(username = username)
    }

    val uiState = rememberStateWithLifecycle(stateFlow = viewModel.uiState)

    val configuration = LocalConfiguration.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        UserDetailHeader(
            onNavigatedBack = onNavigateBack,
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
                uiState.value.userData is DataResult.Success -> {
                    val data = uiState.value.userData!!.value!!

                    NetworkImage(
                        url = data.avatarUrl,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .requiredWidth(
                                configuration.screenWidthDp.dp
                            )
                            .requiredHeight(
                                configuration.screenWidthDp.dp
                            )
                            .graphicsLayer(),
                        imageModifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Box(
                            modifier = Modifier
                                .requiredHeight(configuration.screenWidthDp.dp)
                                .fillMaxWidth()
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxSize()
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
                                    }
                            )

                            Text(
                                text = "@$username",
                                style = CustomTextStyle.header.copy(
                                    color = Color.White,
                                ),
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(horizontal = 24.dp, vertical = 16.dp),
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                                .fillMaxWidth()
                                .background(Color.White)
                        )

                        UserDetailTableRow(former = "Name: ${data.name ?: "-"}", latter = "")

                        UserDetailTableRow(
                            former = "Following: ${data.following}",
                            latter = "Followers: ${data.followers}"
                        )

                        UserDetailTableRow(
                            former = "Public Repos: ${data.publicRepos}",
                            latter = "Company: " + (data.company ?: "-")
                        )

                        UserDetailTableRow(
                            former = "Blog: " + (data.blog ?: "-"),
                            latter = "Location: " + (data.location ?: "-"),
                        )

                        UserDetailTableRow(
                            former = "Email: " + (if (!data.email.isNullOrBlank()) data.email else "-"),
                            latter = "Twitter: " + (data.twitterUsername ?: "-"),
                        )

                        data.bio?.let { bio ->
                            Text(
                                text = "Biography: $bio",
                                style = CustomTextStyle.content,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .padding(24.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .requiredHeight(configuration.screenWidthDp.dp - 100.dp)
                                .fillMaxWidth()
                                .background(Color.White)
                        )
                    }
                }
                uiState.value.userData is DataResult.Error -> {
                    TouchableScale(onClick = {
                        viewModel.fetchUserData(username = username)
                    }) {
                        val errorMessage =
                            if (uiState.value.userData?.exception != null && uiState.value.userData?.exception?.message?.isBlank() == false) {
                                uiState.value.userData!!.exception!!.message!!
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
