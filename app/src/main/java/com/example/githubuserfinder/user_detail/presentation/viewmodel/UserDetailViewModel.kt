package com.example.githubuserfinder.user_detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserfinder.user_detail.data.datasource.UsersRemoteDataSource
import com.example.githubuserfinder.user_detail.data.model.GithubUserDetailModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UserDetailUiState(
    val userData: Result<GithubUserDetailModel>? = null,
    val isLoading: Boolean = false,
)

class UserDetailViewModel(
    private val usersRemoteDataSource: UsersRemoteDataSource
) : ViewModel() {

    var dataFetchingJob: Job? = null

    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState: StateFlow<UserDetailUiState> = _uiState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _uiState.value,
        )

    fun fetchUserData(username: String): Job = viewModelScope.launch {
        _uiState.emit(
            _uiState.value.copy(
                isLoading = true
            )
        )
        ensureActive()
        val result = usersRemoteDataSource.getUser(username)
        ensureActive()
        if (result != null) {
            _uiState.emit(
                _uiState.value.copy(
                    userData = result,
                )
            )
        }
        _uiState.emit(
            _uiState.value.copy(
                isLoading = false,
            )
        )
    }
}
