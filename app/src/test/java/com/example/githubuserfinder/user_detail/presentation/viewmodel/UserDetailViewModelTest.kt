package com.example.githubuserfinder.user_detail.presentation.viewmodel

import app.cash.turbine.test
import com.example.githubuserfinder.user_detail.data.datasource.UsersRemoteDataSource
import com.example.githubuserfinder.user_detail.data.model.GithubUserDetail
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UserDetailViewModelTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var usersRemoteDataSource: UsersRemoteDataSource

    private lateinit var userDetailViewModel: UserDetailViewModel

    private lateinit var sampleGithubUserDetail: GithubUserDetail

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        usersRemoteDataSource = mock()
        userDetailViewModel = UserDetailViewModel(usersRemoteDataSource)
        sampleGithubUserDetail = GithubUserDetail(
            login = "test",
            avatarUrl = "https://test.png",
            followers = 100,
            following = 100,
            publicRepos = 19,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun setUserData_thenUpdateUi(): Unit = runBlocking {

        val expectedData = DataResult.Success(sampleGithubUserDetail)

        val job = launch(Dispatchers.Main) {
            userDetailViewModel
                .uiState
                .test {
                    assertThat(awaitItem().userData).isEqualTo(null)
                    assertThat(awaitItem().userData).isEqualTo(expectedData)

                    cancelAndConsumeRemainingEvents()
                }
        }

        userDetailViewModel.setUserData(expectedData)

        job.join()
        job.cancel()
    }

    @Test
    fun fetchUserData_whenSucceed_thenUpdateUi(): Unit = runBlocking {

        val apiResult = DataResult.Success(sampleGithubUserDetail)

        whenever(usersRemoteDataSource.getUser(any())).thenReturn(apiResult)

        val job = launch(Dispatchers.Main) {
            userDetailViewModel.uiState.test {
                assertThat(awaitItem()).isEqualTo(
                    UserDetailUiState(
                        userData = null,
                        isLoading = false,
                    )
                )

                assertThat(awaitItem()).isEqualTo(
                    UserDetailUiState(
                        userData = null,
                        isLoading = true,
                    )
                )

                assertThat(awaitItem()).isEqualTo(
                    UserDetailUiState(
                        userData = apiResult,
                        isLoading = true,
                    )
                )

                assertThat(awaitItem()).isEqualTo(
                    UserDetailUiState(
                        userData = apiResult,
                        isLoading = false,
                    )
                )
                cancelAndConsumeRemainingEvents()
            }
        }

        userDetailViewModel.fetchUserData(sampleGithubUserDetail.login)
        job.join()
        job.cancel()
    }

    @Test
    fun fetchUserData_whenFailed_thenUpdateUi(): Unit = runBlocking {

        val apiResult = DataResult.Error(exception = Exception("test"))

        whenever(usersRemoteDataSource.getUser(any())).thenReturn(apiResult)

        val job = launch(Dispatchers.Main) {
            userDetailViewModel.uiState.test {

                assertThat(awaitItem()).isEqualTo(
                    UserDetailUiState(
                        userData = null,
                        isLoading = false,
                    )
                )

                assertThat(awaitItem()).isEqualTo(
                    UserDetailUiState(
                        userData = null,
                        isLoading = true,
                    )
                )

                assertThat(awaitItem()).isEqualTo(
                    UserDetailUiState(
                        userData = apiResult,
                        isLoading = true,
                    )
                )

                assertThat(awaitItem()).isEqualTo(
                    UserDetailUiState(
                        userData = apiResult,
                        isLoading = false,
                    )
                )

                cancelAndConsumeRemainingEvents()
            }
        }

        userDetailViewModel.fetchUserData(sampleGithubUserDetail.login)

        job.join()
        job.cancel()
    }
}
