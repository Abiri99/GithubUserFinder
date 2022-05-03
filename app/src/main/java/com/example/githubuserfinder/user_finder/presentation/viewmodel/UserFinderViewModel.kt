package com.example.githubuserfinder.user_finder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UserFinderUiState(
    val searchText: String? = null,
)

class UserFinderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UserFinderUiState())
    val uiState: StateFlow<UserFinderUiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserFinderUiState()
    )

    init {
        observeSearchText()
    }

    private fun observeSearchText() = viewModelScope.launch {
        _uiState
            .filter {
                !it.searchText.isNullOrBlank()
            }
            .collectLatest {
                delay(300)
                fetchUsersWhomNameContains(it.searchText!!)
            }
    }

    private fun fetchUsersWhomNameContains(query: String) {
        // TODO: Implement
    }

    fun setSearchText(value: String) = viewModelScope.launch {
        _uiState.emit(
            _uiState.value.copy(
                searchText = value
            )
        )
    }
}
