package com.example.githubuserfinder.user_finder.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.githubuserfinder.core.presentation.CustomTextStyle
import com.example.githubuserfinder.core.presentation.Emoji
import com.example.githubuserfinder.core.presentation.debugModifier
import com.example.githubuserfinder.core.presentation.rememberStateWithLifecycle
import com.example.githubuserfinder.user_finder.presentation.component.UserFinderAppBar
import com.example.githubuserfinder.user_finder.presentation.viewmodel.UserFinderViewModel

@Composable
fun UserFinderScreen(
    viewModel: UserFinderViewModel,
) {

    val uiState by rememberStateWithLifecycle(stateFlow = viewModel.uiState)

    val onSearchedValueChanged: (String) -> Unit = { value ->
        viewModel.setSearchText(value)
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
        )

        // Content
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            when {
                uiState.searchResult == null -> {
                    Text(
                        text = "Tap the " + Emoji.search + " above and start " + Emoji.eye + " Github users " + Emoji.blackCat,
                        style = CustomTextStyle.contentLargeSemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 24.dp),
                    )
                }
                uiState.searchResult!!.isSuccess -> {
                    // TODO: Implement
                }
                uiState.searchResult!!.isFailure -> {
                    // TODO: Implement
                }
            }

            if (uiState.isSearching) {
                CircularProgressIndicator(
                    modifier = Modifier.requiredSize(48.dp),
                    color = Color.Black,
                    strokeWidth = 4.dp,
                )
            }
        }
    }
}
