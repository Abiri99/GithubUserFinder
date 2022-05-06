package com.example.githubuserfinder.user_finder.presentation.viewmodel

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSource
import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

private const val TAG = "UserFinderViewModel"

data class UserFinderUiState(
    val searchedText: TextFieldValue = TextFieldValue(""),
    val searchResult: Result<GithubSearchResponse>? = null,
    val isSearching: Boolean = false,
)

class UserFinderViewModel(
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : ViewModel() {

    val uiState = MutableStateFlow(UserFinderUiState())

    /**
     * This is a reference to the coroutine which handles the request
     * to the server. Everytime that searchedText updates, we must
     * cancel the previous job by calling job.cancel()
     * */
    private var fetchUsersJob: Job? = null

    init {
        Log.d(TAG, "init called: ${uiState.value}")
        observeSearchedText()
    }

    private fun observeSearchedText() = viewModelScope.launch {
        uiState
            .distinctUntilChanged { old, new -> old.searchedText.text == new.searchedText.text }
            .collectLatest {
                fetchUsersJob?.cancel()
                if (it.searchedText.text.isBlank()) {
                    uiState.emit(
                        uiState.value.copy(
                            searchResult = null,
                            isSearching = false,
                        )
                    )
                } else {
                    delay(600) // This delay is set, so that the application won't request server on every character user enters
                    fetchUsersJob = fetchUsersWhomNameContains(it.searchedText.text)
                }
            }
    }

    fun fetchUsersWhomNameContains(query: String): Job = viewModelScope.launch {
        uiState.emit(
            uiState.value.copy(
                isSearching = true,
            )
        )
        ensureActive()
        val result = searchRemoteDataSource.fetchUsers(query)
        ensureActive()
        if (result != null) {
            uiState.emit(
                uiState.value.copy(
                    isSearching = false,
                    searchResult = result,
                )
            )
        }
        uiState.emit(
            uiState.value.copy(
                isSearching = false,
            )
        )
    }

    fun setSearchText(value: TextFieldValue) = viewModelScope.launch {
        uiState.emit(
            uiState.value.copy(
                searchedText = value,
            )
        )
    }
}
