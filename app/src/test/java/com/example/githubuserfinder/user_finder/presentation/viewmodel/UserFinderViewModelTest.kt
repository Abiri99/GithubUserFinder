package com.example.githubuserfinder.user_finder.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.example.githubuserfinder.core.data.DataResult
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSource
import com.example.githubuserfinder.user_finder.data.model.GithubSearchItem
import com.example.githubuserfinder.user_finder.data.model.GithubSearchResponse
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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UserFinderViewModelTest {

    // TODO: Remove this
    @get:Rule
    var taskExecutor = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    private lateinit var searchRemoteDataSource: SearchRemoteDataSource

    private lateinit var userFinderViewModel: UserFinderViewModel

    private val mockGithubSearchResponse = GithubSearchResponse(
        totalCount = 2,
        incompleteResults = true,
        items = listOf(
            GithubSearchItem(
                login = "JakeWharton",
                id = 66577,
                avatarUrl = "https://avatars.githubusercontent.com/u/66577?v=4",
                score = 100,
            )
        ),
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        userFinderViewModel = UserFinderViewModel(searchRemoteDataSource)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun initialUiState() = runBlocking {
        userFinderViewModel.uiState.test {
            val emission = awaitItem()

            assertThat(emission.searchResult).isEqualTo(null)
            assertThat(emission.isSearching).isEqualTo(false)
            assertThat(emission.searchedText).isEqualTo(TextFieldValue(""))
        }
    }

    @Test
    fun resetUiState_whenDifferentWithCurrentState_thenResetUiState() = runBlocking {
        if (userFinderViewModel.uiState.value != userFinderViewModel.initialUiState) {
            val job = launch(Dispatchers.Main) {
                userFinderViewModel.uiState.test {

                    skipItems(1)

                    val emission = awaitItem()
                    assertThat(emission.isSearching).isEqualTo(false)
                    assertThat(emission.searchResult).isEqualTo(null)

                    cancelAndConsumeRemainingEvents()
                }
            }

            userFinderViewModel.resetUiState()

            job.join()
            job.cancel()
        }
    }

    @Test
    fun setIsSearching_whenDifferentWithCurrentState_thenUpdateUiState() = runBlocking {

        val newState = true

        if (userFinderViewModel.uiState.value.isSearching != newState) {
            val job = launch(Dispatchers.Main) {
                userFinderViewModel.uiState.test {
                    skipItems(1)

                    val emission = awaitItem()
                    assertThat(emission.isSearching).isEqualTo(newState)

                    cancelAndConsumeRemainingEvents()
                }
            }

            userFinderViewModel.setIsSearching(newState)

            job.join()
            job.cancel()
        }
    }

    @Test
    fun setSearchResult_thenUpdateUiState() = runBlocking {

        val searchResult = DataResult.Success(mockGithubSearchResponse)

        val job = launch(Dispatchers.Main) {
            userFinderViewModel.uiState.test {
                skipItems(1)

                val emission = awaitItem()
                assertThat(emission.searchResult).isEqualTo(searchResult)

                cancelAndConsumeRemainingEvents()
            }
        }

        userFinderViewModel.setSearchResult(searchResult)

        job.join()
        job.cancel()
    }

    @Test
    fun setSearchText_thenUpdateUiState() = runBlocking {

        val searchedText = TextFieldValue("JakeWharton")

        val job = launch(Dispatchers.Main) {
            userFinderViewModel.uiState.test {
                skipItems(1)

                val emission = awaitItem()
                assertThat(emission.searchedText).isEqualTo(searchedText)

                cancelAndConsumeRemainingEvents()
            }
        }

        userFinderViewModel.setSearchText(searchedText)

        job.join()
        job.cancel()
    }

    @Test
    fun fetchUsersWhomNameContains_thenUpdateUiState() = runBlocking {

        val searchedText = "j"
        val response = DataResult.Success(mockGithubSearchResponse)

        whenever(searchRemoteDataSource.fetchUsers(searchedText)).thenReturn(response)

        val job = launch(Dispatchers.Main) {
            userFinderViewModel.uiState.test {
                skipItems(1)

                val firstEmission = awaitItem()
                assertThat(firstEmission.isSearching).isEqualTo(true)
                assertThat(firstEmission.searchResult).isEqualTo(null)

                val secondEmission = awaitItem()
                assertThat(secondEmission.isSearching).isEqualTo(true)
                assertThat(secondEmission.searchResult).isEqualTo(response)

                val thirdEmission = awaitItem()
                assertThat(thirdEmission.isSearching).isEqualTo(false)
                assertThat(thirdEmission.searchResult).isEqualTo(response)

                cancelAndConsumeRemainingEvents()
            }
        }

        userFinderViewModel.fetchUsersWhomNameContains(searchedText)

        job.join()
        job.cancel()
    }
}
