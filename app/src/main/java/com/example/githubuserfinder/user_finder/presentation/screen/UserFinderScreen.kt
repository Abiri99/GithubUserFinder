package com.example.githubuserfinder.user_finder.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.core.presentation.CustomTextStyle
import com.example.githubuserfinder.core.presentation.Emoji
import com.example.githubuserfinder.core.presentation.component.TouchableScale
import com.example.githubuserfinder.core.presentation.debugModifier
import com.example.githubuserfinder.core.presentation.rememberStateWithLifecycle
import com.example.githubuserfinder.user_finder.presentation.component.UserFinderAppBar
import com.example.githubuserfinder.user_finder.presentation.component.UserFinderListItem
import com.example.githubuserfinder.user_finder.presentation.viewmodel.UserFinderViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserFinderScreen(
    viewModel: UserFinderViewModel,
    onNavigateToUserDetail: (String) -> Unit,
) {

    val uiState by rememberStateWithLifecycle(stateFlow = viewModel.uiState)

    val onSearchedValueChanged: (TextFieldValue) -> Unit = { value ->
        viewModel.setSearchText(value)
    }

    val onItemClicked: (String) -> Unit = { username ->
        onNavigateToUserDetail(username)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .debugModifier(Modifier.background(Color.Green.copy(0.0f))),
    ) {
        // App Bar
        UserFinderAppBar(
            uiState.searchedText,
            onSearchedValueChanged = onSearchedValueChanged,
            isSearchEnabledInitially = uiState.searchedText.text.isNotBlank(),
            onSearchDismissed = {
                viewModel.setSearchText(TextFieldValue())
            }
        )

        // Content
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            when {
                uiState.isSearching -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .requiredSize(48.dp)
                            .align(Alignment.Center),
                        color = Color.Black,
                        strokeWidth = 4.dp,
                    )
                }
                uiState.searchResult == null -> {
                    Text(
                        text = "Tap the " + Emoji.search + " above and start " + Emoji.eye + "ing" + " Github users " + Emoji.blackCat,
                        style = CustomTextStyle.contentLargeSemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 24.dp),
                    )
                }
                uiState.searchResult is DataResult.Success -> {
                    val result = uiState.searchResult?.value
                    if (result != null && result.items.isNotEmpty()) {
                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(180.dp),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(24.dp),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            items(uiState.searchResult?.value?.items?.size ?: 0) {
                                val item = uiState.searchResult!!.value!!.items[it]
                                UserFinderListItem(
                                    model = item,
                                    onItemClicked = onItemClicked,
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "No result!",
                            style = CustomTextStyle.contentLargeSemiBold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 24.dp),
                        )
                    }
                }
                uiState.searchResult is DataResult.Error -> {
                    TouchableScale(onClick = {
                        viewModel.fetchUsersWhomNameContains(uiState.searchedText.text)
                    }) {
                        val errorMessage =
                            if (uiState.searchResult?.exception != null && uiState.searchResult?.exception?.message?.isBlank() == false) {
                                uiState.searchResult!!.exception!!.message!!
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
            }
        }
    }
}
