package com.example.githubuserfinder.user_detail.data.adapter

import com.google.common.truth.Truth.assertThat
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GithubUserDetailJsonAdapterTest {

    private val sut = GithubUserDetailJsonAdapter()

    @Test
    fun whenCreateEntityFromJson_thenReturnProperResult() {

        val json = JSONObject(
            """
                {
                    "login": "JakeWharton",
                    "id": 66577,
                    "node_id": "MDQ6VXNlcjY2NTc3",
                    "avatar_url": "https://avatars.githubusercontent.com/u/66577?v=4",
                    "gravatar_id": "",
                    "url": "https://api.github.com/users/JakeWharton",
                    "html_url": "https://github.com/JakeWharton",
                    "followers_url": "https://api.github.com/users/JakeWharton/followers",
                    "following_url": "https://api.github.com/users/JakeWharton/following{/other_user}",
                    "gists_url": "https://api.github.com/users/JakeWharton/gists{/gist_id}",
                    "starred_url": "https://api.github.com/users/JakeWharton/starred{/owner}{/repo}",
                    "subscriptions_url": "https://api.github.com/users/JakeWharton/subscriptions",
                    "organizations_url": "https://api.github.com/users/JakeWharton/orgs",
                    "repos_url": "https://api.github.com/users/JakeWharton/repos",
                    "events_url": "https://api.github.com/users/JakeWharton/events{/privacy}",
                    "received_events_url": "https://api.github.com/users/JakeWharton/received_events",
                    "type": "User",
                    "site_admin": false,
                    "name": "Jake Wharton",
                    "company": "@cashapp / @square",
                    "blog": "jakewharton.com",
                    "location": "Pittsburgh, PA, USA",
                    "email": null,
                    "hireable": null,
                    "bio": null,
                    "twitter_username": "JakeWharton",
                    "public_repos": 124,
                    "public_gists": 54,
                    "followers": 62649,
                    "following": 9,
                    "created_at": "2009-03-24T16:09:53Z",
                    "updated_at": "2022-04-29T19:46:01Z"
                }
            """.trimIndent()
        )

        val result = sut.createEntityFromJson(json)

        assertThat(result.avatarUrl).isEqualTo("https://avatars.githubusercontent.com/u/66577?v=4")
        assertThat(result.bio).isEqualTo(null)
        assertThat(result.blog).isEqualTo("jakewharton.com")
        assertThat(result.company).isEqualTo("@cashapp / @square")
        assertThat(result.email).isEqualTo(null)
        assertThat(result.followers).isEqualTo(62649)
        assertThat(result.following).isEqualTo(9)
        assertThat(result.location).isEqualTo("Pittsburgh, PA, USA")
        assertThat(result.login).isEqualTo("JakeWharton")
        assertThat(result.name).isEqualTo("Jake Wharton")
        assertThat(result.publicRepos).isEqualTo(124)
        assertThat(result.twitterUsername).isEqualTo("JakeWharton")
    }
}
