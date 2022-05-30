package com.example.user_finder.presentation

import com.example.core_android.presentation.Emoji
import io.michaelrocks.paranoid.Obfuscate

/**
 * These are all of the strings used in the UserFinder feature and all of them are obfuscated.
 * */
@Obfuscate
internal object UiString {
    const val AppBarTitle = "Github User Finder"
    val InitialScreenMessage = "Let's " + Emoji.eye + " Github users " + Emoji.blackCat
    val FailedToFetchDataDefaultMessage =
        Emoji.womanFacePalming + Emoji.manFacePalming + "\n" + "Failed to fetch data, tap to " + Emoji.refresh
    const val NoResultMessage = "No result!"

    const val StartSearching = "Start searching..."

    val FetchUsersRoute: (String) -> String = {
        "https://api.github.com/search/users?q=$it"
    }

    const val GithubSearchItemIdKey = "id"
    const val GithubSearchItemLoginKey = "login"
    const val GithubSearchItemAvatarUrlKey = "avatar_url"
    const val GithubSearchItemScoreKey = "score"
}