package com.example.githubuserfinder.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubuserfinder.app.navigation.NavArgs
import com.example.githubuserfinder.app.navigation.NavigationDestination
import com.example.githubuserfinder.core.di.daggerViewModel
import com.example.githubuserfinder.user_detail.di.DaggerUserDetailComponent
import com.example.githubuserfinder.user_detail.presentation.screen.UserDetailScreen
import com.example.githubuserfinder.user_finder.di.DaggerUserFinderComponent
import com.example.githubuserfinder.user_finder.presentation.screen.UserFinderScreen

// As this is a small application, a dependency injection framework isn't used.
// Dependencies are created in the root of the UI manually and injected to different features.
@Composable
fun GithubUserFinderApp() {

    val navController = rememberNavController()

    /**
     * This callback must be passed to [UserFinderScreen] so that
     * it would be able to navigate to the detail screen
     * */
    val onNavigateToUserDetail: (String) -> Unit = { username ->
        navController.navigate(
            NavigationDestination.UserDetailNavigationDestination.createRoute(username),
        )
    }

    /**
     * This is callback must be passed to the screens (excluding [NavHost]'s startDestination)
     * so that they can navigate back to the previous screens
     * */
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
            val userFinderComponent = DaggerUserFinderComponent.builder().build()
            val userFinderViewModel = daggerViewModel {
                userFinderComponent.getViewModel()
            }

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
            val userDetailComponent = DaggerUserDetailComponent.builder().build()
            val userDetailViewModel = daggerViewModel {
                userDetailComponent.getViewModel()
            }

            val username = entry.arguments?.getString(NavArgs.Username)
            UserDetailScreen(
                viewModel = userDetailViewModel,
                username = username!!,
                onNavigateBack = onNavigateBack,
            )
        }
    }
}
