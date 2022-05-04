package com.example.githubuserfinder.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubuserfinder.core.data.NetworkRequester
import com.example.githubuserfinder.user_detail.data.datasource.UsersRemoteDataSource
import com.example.githubuserfinder.user_detail.data.datasource.UsersRemoteDataSourceImpl
import com.example.githubuserfinder.user_detail.presentation.screen.UserDetailScreen
import com.example.githubuserfinder.user_detail.presentation.viewmodel.UserDetailViewModel
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSource
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSourceImpl
import com.example.githubuserfinder.user_finder.presentation.screen.UserFinderScreen
import com.example.githubuserfinder.user_finder.presentation.viewmodel.UserFinderViewModel

// Dependencies are created manually in the main activity and injected to the lower layers
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            val networkRequester = NetworkRequester()

            val searchRemoteDataSource: SearchRemoteDataSource =
                SearchRemoteDataSourceImpl(networkRequester = networkRequester)

            val usersRemoteDataSource: UsersRemoteDataSource =
                UsersRemoteDataSourceImpl(networkRequester = networkRequester)

            val userFinderViewModel = UserFinderViewModel(
                searchRemoteDataSource = searchRemoteDataSource,
            )

            val userDetailViewModel = UserDetailViewModel(
                usersRemoteDataSource = usersRemoteDataSource,
            )

            NavHost(
                navController = navController,
                startDestination = ScreenName.UserFinderScreen,
            ) {
                composable(
                    route = NavigationDestination.UserFinderNavigationDestination.routeTemplate,
                ) {
                    UserFinderScreen(
                        navController = navController,
                        viewModel = userFinderViewModel,
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
                        navController = navController,
                        username = username!!,
                    )
                }
            }
        }
    }
}
