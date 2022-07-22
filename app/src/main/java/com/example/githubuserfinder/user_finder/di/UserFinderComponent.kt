package com.example.githubuserfinder.user_finder.di

import com.example.githubuserfinder.user_finder.presentation.viewmodel.UserFinderViewModel
import dagger.Component

@UserFinderScope
@Component(modules = [UserFinderModule::class])
interface UserFinderComponent {
    @Component.Builder
    interface Builder {
        fun build(): UserFinderComponent
    }

    fun getViewModel(): UserFinderViewModel
}
