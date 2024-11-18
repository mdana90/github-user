package com.dana.githubuser.network.mapper

import com.dana.githubuser.model.User
import com.dana.githubuser.network.response.UserResponse

fun UserResponse.toUser() : User {
    return User(
        id = id,
        username = login,
        avatarUrl = avatarUrl,
        name = name,
        followers = followers,
        following = following
    )
}