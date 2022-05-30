package com.example.githubuserfinder.user_finder.data.adapter

import com.google.common.truth.Truth.assertThat
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GithubSearchResponseModelJsonAdapterTest {

    private lateinit var githubSearchItemJsonAdapter: GithubSearchItemJsonAdapter

    private lateinit var sut: GithubSearchResponseJsonAdapter

    @Before
    fun setup() {
        githubSearchItemJsonAdapter = mock()
        sut = GithubSearchResponseJsonAdapter(githubSearchItemJsonAdapter)
    }

    @Test
    fun whenCreateEntityFromJson_thenReturnProperEntity() {

        val fakeGithubSearchItem = GithubSearchItemModel(
            id = 100,
            login = "JakeWharton",
            avatarUrl = "https://google.com",
            score = 200,
        )

        val json = JSONObject(
            """
                {
                    "total_count": 45806,
                    "incomplete_results": false,
                    "items": [
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
                    ]
                }
            """.trimIndent()
        )

        whenever(githubSearchItemJsonAdapter.createEntityFromJson(any())).thenReturn(
            fakeGithubSearchItem
        )

        val expectedResult = sut.createEntityFromJson(json)

        assertThat(expectedResult.totalCount).isEqualTo(45806)
        assertThat(expectedResult.incompleteResults).isEqualTo(false)
        assertThat(expectedResult.itemModels.size).isEqualTo(1)
        assertThat(expectedResult.itemModels.first()).isEqualTo(fakeGithubSearchItem)
    }
}
