package com.example.githubuserfinder.user_detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.user_detail.data.datasource.UsersRemoteDataSource
import com.example.githubuserfinder.user_detail.data.model.GithubUserDetailModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class UserDetailUiState(
    val userData: DataResult<GithubUserDetailModel>? = null,
    val isLoading: Boolean = false,
)

class UserDetailViewModel(
    private val usersRemoteDataSource: UsersRemoteDataSource
) : ViewModel() {

    var dataFetchingJob: Job? = null

    val uiState = MutableStateFlow(UserDetailUiState())

    fun cancelFetchingData() = dataFetchingJob?.cancel()

    fun setLoading(value: Boolean) = viewModelScope.launch {
        uiState.emit(
            uiState.value.copy(
                isLoading = value,
            )
        )
    }

    fun setUserData(data: DataResult<GithubUserDetailModel>) = viewModelScope.launch {
        uiState.emit(
            uiState.value.copy(
                userData = data,
            )
        )
    }

    fun fetchUserData(username: String): Job = viewModelScope.launch {
        setLoading(true)
        ensureActive()
        val result = usersRemoteDataSource.getUser(username)
        ensureActive()
        setUserData(result)
        setLoading(false)
    }
}
