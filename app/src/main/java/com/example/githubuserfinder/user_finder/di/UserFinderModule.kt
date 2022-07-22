package com.example.githubuserfinder.user_finder.di

import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSource
import com.example.githubuserfinder.user_finder.data.datasource.SearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UserFinderModule {

    @UserFinderScope
    @Binds
    abstract fun bindSearchRemoteDataSource(searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl): SearchRemoteDataSource
}
