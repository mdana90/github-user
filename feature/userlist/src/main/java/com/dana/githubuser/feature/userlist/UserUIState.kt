package com.dana.githubuser.feature.userlist

import com.dana.githubuser.model.User

data class UserUIState(
    val username: String,
    val avatarUrl: String
)

fun User.toUIState() = UserUIState(
    username = username,
    avatarUrl = avatarUrl
)