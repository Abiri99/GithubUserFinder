package com.example.githubuserfinder.user_finder.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "UserFinderViewModel"

data class UserFinderUiState(
    val searchText: String? = null,
)

class UserFinderViewModel(
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : ViewModel() {

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

    private fun fetchUsersWhomNameContains(query: String) = viewModelScope.launch {
        val result = searchRemoteDataSource.fetchUsers(query)
        when {
            result.isSuccess -> {
                Log.d(TAG, "successful response: ${result.getOrNull()?.toString()}")
            }
            result.isFailure -> {}
        }
    }

    fun setSearchText(value: String) = viewModelScope.launch {
        _uiState.emit(
            _uiState.value.copy(
                searchText = value
            )
        )
    }
}
