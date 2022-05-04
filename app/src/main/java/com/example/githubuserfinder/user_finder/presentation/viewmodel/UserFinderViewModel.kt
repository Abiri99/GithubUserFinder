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
import kotlinx.coroutines.flow.distinctUntilChanged
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
            .distinctUntilChanged { old, new -> old.searchedText == new.searchedText }
            .collectLatest {
                if (it.searchedText.isNullOrBlank()) {
                    _uiState.emit(
                        _uiState.value.copy(
                            searchResult = null,
                            isSearching = false,
                        )
                    )
                } else {
                    delay(600) // This delay is set, so that the application won't request server on every character user enters
                    fetchUsersWhomNameContains(it.searchedText)
                }
            }
    }

    fun fetchUsersWhomNameContains(query: String, page: Int = 1) = viewModelScope.launch {
        _uiState.emit(
            _uiState.value.copy(
                isSearching = true,
            )
        )
        val result = searchRemoteDataSource.fetchUsers(query, page)
        _uiState.emit(
            _uiState.value.copy(
                isSearching = false,
                searchResult = result,
            )
        )
    }

    fun setSearchText(value: String) = viewModelScope.launch {
        _uiState.emit(
            _uiState.value.copy(
                searchedText = value
            )
        )
    }
}
