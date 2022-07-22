package com.example.githubuserfinder.user_detail.di

import com.example.githubuserfinder.user_detail.presentation.viewmodel.UserDetailViewModel
import dagger.Component

@UserDetailScope
@Component(modules = [UserDetailModule::class])
interface UserDetailComponent {
    @Component.Builder
    interface Builder {
        fun build(): UserDetailComponent
    }

    fun getViewModel(): UserDetailViewModel
}