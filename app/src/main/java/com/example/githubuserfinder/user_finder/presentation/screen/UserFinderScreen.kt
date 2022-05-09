package com.example.githubuserfinder.user_finder.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.core.presentation.component.TouchableScale
import com.example.githubuserfinder.core.presentation.mapper.ExceptionMessageMapper
import com.example.githubuserfinder.core.presentation.util.ComposeUtil
import com.example.githubuserfinder.core.presentation.util.Emoji
import com.example.githubuserfinder.core.presentation.util.SystemUiUtil
import com.example.githubuserfinder.user_finder.presentation.component.CustomAppBar
import com.example.githubuserfinder.user_finder.presentation.component.UserFinderBottomSearchBar
import com.example.githubuserfinder.user_finder.presentation.component.UserFinderListItem
import com.example.githubuserfinder.user_finder.presentation.viewmodel.UserFinderViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserFinderScreen(
    viewModel: UserFinderViewModel,
    onNavigateToUserDetail: (String) -> Unit,
) {

    SystemUiUtil.ConfigStatusBar(
        color = MaterialTheme.colors.primaryVariant
    )

    val focusManager = LocalFocusManager.current

    val uiState by ComposeUtil.rememberStateWithLifecycle(stateFlow = viewModel.uiState)

    val onSearchedValueChanged: (TextFieldValue) -> Unit = { value ->
        viewModel.setSearchText(value)
    }

    val onItemClicked: (String) -> Unit = { username ->
        focusManager.clearFocus()
        onNavigateToUserDetail(username)
    }

    // Content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {

        Box(
            modifier = Modifier.padding(top = 64.dp)
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
                        text = "Let's " + Emoji.eye + " Github users " + Emoji.blackCat,
                        style = MaterialTheme.typography.h6,
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
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            items(uiState.searchResult?.value?.items?.size ?: 0) {
                                val item = uiState.searchResult!!.value!!.items[it]
                                UserFinderListItem(
                                    model = item,
                                    onItemClicked = onItemClicked,
                                )
                            }

                            item {
                                Spacer(modifier = Modifier.padding(bottom = 100.dp))
                            }
                        }
                    } else {
                        Text(
                            text = "No result!",
                            style = MaterialTheme.typography.h6,
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
                            if (uiState.searchResult?.exception != null) {
                                ExceptionMessageMapper.getProperMessageForException(uiState.searchResult!!.exception!!)
                            } else {
                                Emoji.womanFacePalming + Emoji.manFacePalming + "\n" + "Failed to fetch data, tap to " + Emoji.refresh
                            }
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.h6,
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

        // App Bar
        CustomAppBar(
            title = "Github User Finder",
        )

        // Bottom Search Bar
        UserFinderBottomSearchBar(
            searchedValue = uiState.searchedText,
            onSearchedValueChanged = onSearchedValueChanged,
        )

        // User Detail Sheet

    }
}
