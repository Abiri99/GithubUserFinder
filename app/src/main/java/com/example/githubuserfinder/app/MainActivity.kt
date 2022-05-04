package com.example.githubuserfinder.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSourceImpl
import com.example.githubuserfinder.user_finder.presentation.screen.UserFinderScreen
import com.example.githubuserfinder.user_finder.presentation.viewmodel.UserFinderViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = NavDestinations.UserFinderScreen,
            ) {
                composable(
                    route = NavDestinations.UserFinderScreen,
                ) {
                    UserFinderScreen(
                        viewModel = UserFinderViewModel(
                            searchRemoteDataSource = SearchRemoteDataSourceImpl()
                        ),
                    )
                }
            }
        }
    }
}
