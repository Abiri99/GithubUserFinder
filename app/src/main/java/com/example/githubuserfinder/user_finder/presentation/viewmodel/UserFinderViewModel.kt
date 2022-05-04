package com.example.githubuserfinder.user_finder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSource
import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "UserFinderViewModel"

internal data class UserFinderUiState(
    val searchedText: String? = null,
    val searchResult: Result<GithubSearchResponse>? = null,
    val isSearching: Boolean = false,
)

class UserFinderViewModel(
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserFinderUiState())
    internal val uiState: StateFlow<UserFinderUiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserFinderUiState()
    )

    init {
        observeSearchedText()
    }

    private fun observeSearchedText() = viewModelScope.launch {
        _uiState
            .filter {
                !it.searchedText.isNullOrBlank()
            }
            .collectLatest {
                delay(600) // This delay is set, so that the application won't request server on every character user enters
                fetchUsersWhomNameContains(it.searchedText!!)
            }
    }

    private fun fetchUsersWhomNameContains(query: String) = viewModelScope.launch {
        val result = searchRemoteDataSource.fetchUsers(query)
        when {
            result.isSuccess -> {
                // TODO: Implement
            }
            result.isFailure -> {
                // TODO: Implement
            }
        }
    }

    fun setSearchText(value: String) = viewModelScope.launch {
        _uiState.emit(
            _uiState.value.copy(
                searchedText = value
            )
        )
    }
}
