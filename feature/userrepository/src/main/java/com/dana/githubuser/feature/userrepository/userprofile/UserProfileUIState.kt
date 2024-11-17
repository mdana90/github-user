package com.dana.githubuser.feature.userrepository.userprofile

import com.dana.githubuser.model.User

sealed interface UserProfileUIState {
    data object Loading : UserProfileUIState
    data class Success(private val user: User) : UserProfileUIState {
        val username: String
            get() = user.username

        val name: String?
            get() = user.name

        val avatarUrl: String
            get() = user.avatarUrl

        val followers: Int
            get() = user.followers

        val following: Int
            get() = user.following
    }
}