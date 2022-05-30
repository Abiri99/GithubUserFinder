package com.example.githubuserfinder.user_detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserfinder.user_detail.data.datasource.UsersRemoteDataSource
import com.example.githubuserfinder.user_detail.data.model.GithubUserDetail
import com.example.githubuserfinder.user_detail.presentation.screen.UserDetailScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * This is a data class that wrapped all of the UI states in the [UserDetailScreen].
 * All of the fields are immutable so that they wouldn't violate Functional Programming rules.
 * */
data class UserDetailUiState(
    val userData: DataResult<GithubUserDetail>? = null,
    val isLoading: Boolean = false,
)

/**
 * This class contains all of the [UserDetailScreen] functionalities
 * */
class UserDetailViewModel(
    private val usersRemoteDataSource: UsersRemoteDataSource
) : ViewModel() {

    private var dataFetchingJob: Job? = null

    val uiState = MutableStateFlow(UserDetailUiState())

    /**
     * This method is used when user navigates back at
     * the time the data is being fetched from the server
     * */
    fun cancelFetchingData() = dataFetchingJob?.cancel()

    private fun setLoading(value: Boolean) = viewModelScope.launch {
        uiState.emit(
            uiState.value.copy(
                isLoading = value,
            )
        )
    }

    fun setUserData(data: DataResult<GithubUserDetail>) = viewModelScope.launch {
        uiState.emit(
            uiState.value.copy(
                userData = data,
            )
        )
    }

    /**
     * This method fetches user profile data from the remote server
     * */
    fun fetchUserData(username: String): Job = viewModelScope.launch {
        setLoading(true)
        ensureActive()
        val result = usersRemoteDataSource.getUser(username)
        ensureActive()
        setUserData(result)
        setLoading(false)
    }
}
