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
import androidx.compose.material.MaterialTheme
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
import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.core.presentation.component.NetworkImage
import com.example.githubuserfinder.core.presentation.component.TouchableScale
import com.example.githubuserfinder.core.presentation.mapper.ExceptionMessageMapper
import com.example.githubuserfinder.core.presentation.util.ComposeUtil
import com.example.githubuserfinder.user_detail.presentation.UserDetailString
import com.example.githubuserfinder.user_detail.presentation.component.UserDetailTableRow
import com.example.githubuserfinder.user_detail.presentation.viewmodel.UserDetailViewModel
import com.example.githubuserfinder.user_finder.presentation.component.CustomAppBar

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel,
    username: String,
    onNavigateBack: () -> Unit,
) {

    BackHandler {
        viewModel.cancelFetchingData()
        onNavigateBack()
    }

    // This is supposed the be run only once
    LaunchedEffect(true) {
        viewModel.fetchUserData(username = username)
    }

    val uiState = ComposeUtil.rememberStateWithLifecycle(stateFlow = viewModel.uiState)

    val configuration = LocalConfiguration.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center,
    ) {

        val surfaceColor = MaterialTheme.colors.surface
        val statusBarColor = MaterialTheme.colors.primaryVariant

        when {
            uiState.value.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .requiredSize(48.dp)
                        .align(Alignment.Center),
                    color = Color.White,
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
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                val topGradient = Brush.verticalGradient(
                                    colors = listOf(statusBarColor, Color.Transparent),
                                    startY = 0f,
                                    endY = 3 * size.height / 4,
                                )
                                drawRect(topGradient)
                            }
                        }
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

                                        val bottomGradient = Brush.verticalGradient(
                                            colors = listOf(surfaceColor, Color.Transparent),
                                            startY = size.height,
                                            endY = size.height / 3,
                                        )
                                        drawRect(bottomGradient)
                                    }
                                }
                        )

                        Text(
                            text = UserDetailString.Username(username),
                            style = MaterialTheme.typography.h6.copy(
                                color = MaterialTheme.colors.onSurface,
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
                            .background(surfaceColor)
                    )

                    UserDetailTableRow(former = UserDetailString.Name(data.name), latter = "")

                    UserDetailTableRow(
                        former = UserDetailString.Following(data.following),
                        latter = UserDetailString.Followers(data.followers)
                    )

                    UserDetailTableRow(
                        former = UserDetailString.PublicRepos(data.publicRepos),
                        latter = UserDetailString.Company(data.company)
                    )

                    UserDetailTableRow(
                        former = UserDetailString.Blog(data.blog),
                        latter = UserDetailString.Location(data.location),
                    )

                    UserDetailTableRow(
                        former = UserDetailString.Email(data.email),
                        latter = UserDetailString.TwitterUserName(data.twitterUsername),
                    )

                    data.bio?.let { bio ->
                        Text(
                            text = UserDetailString.Bio(bio),
                            style = MaterialTheme.typography.body2.copy(
                                color = MaterialTheme.colors.onSurface,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(surfaceColor)
                                .padding(24.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .requiredHeight(configuration.screenWidthDp.dp - 100.dp)
                            .fillMaxWidth()
                            .background(surfaceColor)
                    )
                }
            }
            uiState.value.userData is DataResult.Error -> {
                TouchableScale(onClick = {
                    viewModel.fetchUserData(username = username)
                }) {
                    val errorMessage =
                        if (uiState.value.userData?.exception != null) {
                            ExceptionMessageMapper.getProperMessageForException(uiState.value.userData!!.exception!!)
                        } else {
                            UserDetailString.FailedToFetchDataErrorMessage
                        }
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.h6,
                        color = Color.White,
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

        CustomAppBar(
            title = username,
            onNavigateUp = onNavigateBack,
            isTransparent = true,
        )
    }
}
