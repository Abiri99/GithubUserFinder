package com.example.githubuserfinder.user_finder.presentation.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSource
import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

data class UserFinderUiState(
    val searchedText: TextFieldValue = TextFieldValue(""),
    val searchResult: DataResult<GithubSearchResponse>? = null,
    val isSearching: Boolean = false,
)

class UserFinderViewModel(
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : ViewModel() {

    val initialUiState = UserFinderUiState()
    val uiState = MutableStateFlow(initialUiState)

    /**
     * This is a reference to the coroutine which handles the request
     * to the server. Everytime that searchedText updates, we must
     * cancel the previous job by calling job.cancel()
     * */
    private var fetchUsersJob: Job? = null

    init {
        observeSearchedText()
    }

    private fun observeSearchedText() = viewModelScope.launch {
        uiState
            .distinctUntilChanged { old, new -> old.searchedText.text == new.searchedText.text }
            .collectLatest {
                fetchUsersJob?.cancel()
                if (it.searchedText.text.isBlank()) {
                    resetUiState()
                } else {
                    delay(600) // This delay is set, so that the application won't request server on every character user enters
                    fetchUsersJob = fetchUsersWhomNameContains(it.searchedText.text)
                }
            }
    }

    fun resetUiState() = viewModelScope.launch {
        uiState.emit(
            uiState.value.copy(
                searchResult = null,
                searchedText = TextFieldValue(""),
                isSearching = false,
            )
        )
    }

    fun setIsSearching(value: Boolean) = viewModelScope.launch {
        uiState.emit(
            uiState.value.copy(
                isSearching = value
            )
        )
    }

    fun setSearchResult(value: DataResult<GithubSearchResponse>?) = viewModelScope.launch {
        uiState.emit(
            uiState.value.copy(
                searchResult = value,
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

    fun fetchUsersWhomNameContains(query: String): Job = viewModelScope.launch {
        setIsSearching(true)
        ensureActive()
        val result = searchRemoteDataSource.fetchUsers(query)
        ensureActive()
        setSearchResult(result)
        setIsSearching(false)
    }
}
