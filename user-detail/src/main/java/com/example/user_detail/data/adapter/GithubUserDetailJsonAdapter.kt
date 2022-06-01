package com.example.user_detail.data.adapter

import com.example.core.data.JsonAdapter
import com.example.core.data.getStringNullable
import com.example.user_detail.data.datasource.UsersRemoteDataSource
import com.example.user_detail.data.model.GithubUserDetail
import com.example.user_detail.presentation.UserDetailString
import org.json.JSONObject

/**
 * This class is used in [UsersRemoteDataSource] and handles
 * converting a [JSONObject] to a [GithubUserDetail] model.
 * */
class GithubUserDetailJsonAdapter : JsonAdapter<GithubUserDetail> {
    override fun createEntityFromJson(json: JSONObject): GithubUserDetail {
        return GithubUserDetail(
            login = json.getString(UserDetailString.UserDetailLoginKey),
            avatarUrl = json.getString(UserDetailString.UserDetailAvatarUrlKey),
            following = json.getInt(UserDetailString.UserDetailFollowingKey),
            followers = json.getInt(UserDetailString.UserDetailFollowersKey),
            publicRepos = json.getInt(UserDetailString.UserDetailPublicReposKey),
            name = json.getStringNullable(UserDetailString.UserDetailNameKey),
            company = json.getStringNullable(UserDetailString.UserDetailCompanyKey),
            blog = json.getStringNullable(UserDetailString.UserDetailBlogKey),
            location = json.getStringNullable(UserDetailString.UserDetailLocationKey),
            email = json.getStringNullable(UserDetailString.UserDetailEmailKey),
            twitterUsername = json.getStringNullable(UserDetailString.UserDetailTwitterUsernameKey),
            bio = json.getStringNullable(UserDetailString.UserDetailBioKey),
        )
    }
}
