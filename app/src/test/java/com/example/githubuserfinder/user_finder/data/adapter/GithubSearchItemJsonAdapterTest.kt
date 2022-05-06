package com.example.githubuserfinder.user_finder.data.adapter

import com.google.common.truth.Truth.assertThat
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GithubSearchItemJsonAdapterTest {

    private lateinit var sut: GithubSearchItemJsonAdapter

    @Before
    fun setup() {
        sut = GithubSearchItemJsonAdapter()
    }

    @Test
    fun whenCreateEntityFromJson_thenReturnProperModel() {
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
                    "score": 1.0
                }
            """.trimIndent()
        )

        val result = sut.createEntityFromJson(json)

        assertThat(result.id).isEqualTo(66577)
        assertThat(result.avatarUrl).isEqualTo("https://avatars.githubusercontent.com/u/66577?v=4")
        assertThat(result.login).isEqualTo("JakeWharton")
        assertThat(result.score).isEqualTo(1)
    }
}
