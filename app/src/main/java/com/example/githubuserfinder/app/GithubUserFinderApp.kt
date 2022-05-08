package com.example.githubuserfinder.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubuserfinder.app.navigation.NavArgs
import com.example.githubuserfinder.app.navigation.NavigationDestination
import com.example.githubuserfinder.core.data.NetworkRequester
import com.example.githubuserfinder.user_detail.data.adapter.GithubUserDetailJsonAdapter
import com.example.githubuserfinder.user_detail.data.datasource.UsersRemoteDataSource
import com.example.githubuserfinder.user_detail.data.datasource.UsersRemoteDataSourceImpl
import com.example.githubuserfinder.user_detail.presentation.screen.UserDetailScreen
import com.example.githubuserfinder.user_detail.presentation.viewmodel.UserDetailViewModel
import com.example.githubuserfinder.user_finder.data.adapter.GithubSearchItemJsonAdapter
import com.example.githubuserfinder.user_finder.data.adapter.GithubSearchResponseJsonAdapter
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSource
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSourceImpl
import com.example.githubuserfinder.user_finder.presentation.screen.UserFinderScreen
import com.example.githubuserfinder.user_finder.presentation.viewmodel.UserFinderViewModel

@Composable
fun GithubUserFinderApp() {

    val navController = rememberNavController()

    val networkRequester = NetworkRequester()

    val githubSearchItemJsonAdapter = GithubSearchItemJsonAdapter()
    val githubSearchResponseJsonAdapter =
        GithubSearchResponseJsonAdapter(githubSearchItemJsonAdapter = githubSearchItemJsonAdapter)
    val githubUserDetailJsonAdapter = GithubUserDetailJsonAdapter()

    val searchRemoteDataSource: SearchRemoteDataSource =
        SearchRemoteDataSourceImpl(
            networkRequester = networkRequester,
            githubSearchResponseJsonAdapter = githubSearchResponseJsonAdapter,
        )

    val usersRemoteDataSource: UsersRemoteDataSource =
        UsersRemoteDataSourceImpl(
            networkRequester = networkRequester,
            githubUserDetailJsonAdapter = githubUserDetailJsonAdapter,
        )

    val userFinderViewModel = UserFinderViewModel(
        searchRemoteDataSource = searchRemoteDataSource,
    )

    val userDetailViewModel = UserDetailViewModel(
        usersRemoteDataSource = usersRemoteDataSource,
    )

    val onNavigateToUserDetail: (String) -> Unit = { username ->
        navController.navigate(
            NavigationDestination.UserDetailNavigationDestination.createRoute(
                username = username
            ),
        )
    }

    val onNavigateBack: () -> Unit = {
        navController.navigateUp()
    }

    NavHost(
        navController = navController,
        startDestination = NavigationDestination.UserFinderNavigationDestination.routeTemplate,
    ) {
        composable(
            route = NavigationDestination.UserFinderNavigationDestination.routeTemplate,
        ) {
            UserFinderScreen(
                viewModel = userFinderViewModel,
                onNavigateToUserDetail = onNavigateToUserDetail,
            )
        }

        composable(
            route = NavigationDestination.UserDetailNavigationDestination.routeTemplate,
            arguments = listOf(
                navArgument(name = NavArgs.Username) {
                    nullable = false
                    type = NavType.StringType
                }
            ),
        ) { entry ->
            val username = entry.arguments?.getString(NavArgs.Username)
            UserDetailScreen(
                viewModel = userDetailViewModel,
                username = username!!,
                onNavigateBack = onNavigateBack,
            )
        }
    }
}
