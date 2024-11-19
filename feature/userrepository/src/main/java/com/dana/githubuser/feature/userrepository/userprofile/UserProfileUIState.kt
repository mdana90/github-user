package com.dana.githubuser.feature.userrepository.userprofile

import com.dana.githubuser.common.formatToKorM
import com.dana.githubuser.model.User

sealed interface UserProfileUIState {
    data object Loading : UserProfileUIState
    data class Error(val message: String): UserProfileUIState
    data class Success(
        val username: String,
        val name: String?,
        val avatarUrl: String,
        val followers: Int,
        val following: Int
    ) : UserProfileUIState {
        val formattedFollowers: String
            get() = formatToKorM(followers.toLong())

        val formattedFollowing: String
            get() = formatToKorM(following.toLong())
    }
}

internal fun User.toUIState() = UserProfileUIState.Success(
    username = username,
    name = name,
    avatarUrl = avatarUrl,
    followers = followers,
    following = following
)