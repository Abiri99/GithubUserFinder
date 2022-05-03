package com.example.githubuserfinder.user_finder.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.githubuserfinder.core.debugModifier
import com.example.githubuserfinder.user_finder.presentation.component.UserFinderAppBar
import com.example.githubuserfinder.user_finder.presentation.viewmodel.UserFinderViewModel
import ir.itoll.mycarapp.core.presentation.util.rememberStateWithLifecycle

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
            uiState.searchText,
            onSearchedValueChanged = onSearchedValueChanged,
        )

        // Content
        // TODO: Implement
    }
}
