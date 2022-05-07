package com.example.githubuserfinder.app.navigation

sealed class NavigationDestination(
    val routeTemplate: String,
) {

    object UserFinderNavigationDestination : NavigationDestination(routeTemplate = ScreenName.UserFinderScreen)

    object UserDetailNavigationDestination :
        NavigationDestination(routeTemplate = "${ScreenName.UserDetailScreen}/{${NavArgs.Username}}") {
        fun createRoute(username: String): String {
            return "${ScreenName.UserDetailScreen}/$username"
        }
    }
}
