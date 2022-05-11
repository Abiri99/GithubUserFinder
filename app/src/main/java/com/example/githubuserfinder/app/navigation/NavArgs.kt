package com.example.githubuserfinder.app.navigation

import com.example.githubuserfinder.user_detail.presentation.screen.UserDetailScreen
import io.michaelrocks.paranoid.Obfuscate

/**
 * This class contains the keys of the navigation arguments.
 * For example for [UserDetailScreen], such a route template is defined: "[ScreenName.UserDetailScreen]/[NavArgs.Username]"
 * */
@Obfuscate
object NavArgs {

    const val Username = "username"
}
