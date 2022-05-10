package com.example.githubuserfinder.app.navigation

import androidx.navigation.NavController

/**
 * This class is a container for different screens.
 * @param routeTemplate contains a template which we pass to the [NavController.navigate] method
 *
 * Each [NavigationDestination] sub class may override a method called [createRoute] which is an
 * abstraction on top of [routeTemplate].
 *
 * The [createRoute] method is used to a route to navigate to, so that the UI layer doesn't need
 * to know about the [routeTemplate] of the [NavigationDestination] it wants to navigate to.
 * */
sealed class NavigationDestination(
    val routeTemplate: String,
) {

    open fun createRoute(vararg args: String): String {
        return routeTemplate
    }

    object UserFinderNavigationDestination :
        NavigationDestination(routeTemplate = ScreenName.UserFinderScreen)

    object UserDetailNavigationDestination :
        NavigationDestination(routeTemplate = "${ScreenName.UserDetailScreen}/{${NavArgs.Username}}") {
        override fun createRoute(vararg args: String): String {
            return "${ScreenName.UserDetailScreen}/${args[0]}"
        }
    }
}
