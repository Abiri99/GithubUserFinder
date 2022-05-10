package com.example.githubuserfinder.user_finder.presentation

import com.example.githubuserfinder.core.presentation.util.Emoji
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
object UserFinderString {
    const val ViewModelTag = "UserFinderViewModel"

    const val AppBarTitle = "Github User Finder"
    val InitialScreenMessage = "Let's " + Emoji.eye + " Github users " + Emoji.blackCat
    val FailedToRetrieveDataDefaultMessage =
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
