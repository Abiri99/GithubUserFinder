package com.example.user_detail.presentation

import com.example.core.presentation.util.Emoji
import io.michaelrocks.paranoid.Obfuscate

/**
 * These are all of the strings used in the UserDetail feature and all of them are obfuscated.
 * */
@Obfuscate
object UserDetailString {

    val GetUserApiRoute: (String) -> String = {
        "https://api.github.com/users/$it"
    }

    val FailedToFetchDataErrorMessage =
        Emoji.womanFacePalming + Emoji.manFacePalming + "\n" + "Failed to fetch data, tap to " + Emoji.refresh

    const val UserDetailLoginKey = "login"
    const val UserDetailAvatarUrlKey = "avatar_url"
    const val UserDetailFollowingKey = "following"
    const val UserDetailFollowersKey = "followers"
    const val UserDetailPublicReposKey = "public_repos"
    const val UserDetailNameKey = "name"
    const val UserDetailCompanyKey = "company"
    const val UserDetailBlogKey = "blog"
    const val UserDetailLocationKey = "location"
    const val UserDetailEmailKey = "email"
    const val UserDetailTwitterUsernameKey = "twitter_username"
    const val UserDetailBioKey = "bio"

    val Username: (String) -> String = {
        "@$it"
    }

    val Name: (String?) -> String = {
        "Name: $it"
    }

    val Following: (Int) -> String = {
        "Following: $it"
    }

    val Followers: (Int) -> String = {
        "Followers: $it"
    }

    val PublicRepos: (Int) -> String = {
        "Public Repos: $it"
    }

    val Company: (String?) -> String = {
        "Company: ${it ?: "-"}"
    }

    val Blog: (String?) -> String = {
        "Blog: ${it ?: "-"}"
    }

    val Location: (String?) -> String = {
        "Location: ${it ?: "-"}"
    }

    val Email: (String?) -> String = {
        "Email: ${it ?: "-"}"
    }

    val TwitterUserName: (String?) -> String = {
        "Twitter: ${it ?: "-"}"
    }

    val Bio: (String) -> String = {
        "Biography: $it"
    }
}
