package com.example.githubuserfinder.user_finder.presentation.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSource
import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponseModel
import com.example.githubuserfinder.user_finder.presentation.screen.UserFinderScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * This is a data class that wrapped all of the UI states in the [UserFinderScreen].
 * All of the fields are immutable so that they wouldn't violate Functional Programming rules.
 * */
data class UserFinderUiState(
    val searchedText: TextFieldValue = TextFieldValue(""),
    val searchResult: DataResult<GithubSearchResponseModel>? = null,
    val isSearching: Boolean = false,
)

/**
 * This class contains all of the [UserFinderScreen] functionalities
 * */
class UserFinderViewModel(
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : ViewModel() {

    val initialUiState = UserFinderUiState()
    val uiState = MutableStateFlow(initialUiState)

    /**
     * This is a reference to the coroutine which handles the request
     * to the server. Everytime that searchedText updates,the
     * previous job must be cancelled by calling job.cancel()
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
                    // This is the case when user have deleted the searched text. so the ui state must be reset
                    resetUiState()
                } else {
                    delay(600) // This delay is set, so that the application won't request server on every character user enters
                    fetchUsersJob = fetchUsersWhomNameContains(it.searchedText.text)
                }
            }
    }

    /**
     * This is used to reset the UI state when user have cleared the query text
     * */
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

    fun setSearchResult(value: DataResult<GithubSearchResponseModel>?) = viewModelScope.launch {
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

    /**
     * This method requests server to fetch the users whom name contains the [query].
     * */
    fun fetchUsersWhomNameContains(query: String): Job = viewModelScope.launch {
        setIsSearching(true)
        ensureActive() // This ensures that the coroutine is not cancelled
        val result = searchRemoteDataSource.fetchUsers(query)
        ensureActive() // This ensures that the coroutine is not cancelled
        setSearchResult(result)
        setIsSearching(false)
    }
}
