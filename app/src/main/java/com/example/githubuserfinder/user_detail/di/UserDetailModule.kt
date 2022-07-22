package com.example.githubuserfinder.user_detail.di

import com.example.githubuserfinder.user_detail.data.datasource.UsersRemoteDataSource
import com.example.githubuserfinder.user_detail.data.datasource.UsersRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UserDetailModule {

    @UserDetailScope
    @Binds
    abstract fun bindUserRemoteDataSource(usersRemoteDataSourceImpl: UsersRemoteDataSourceImpl): UsersRemoteDataSource
}
