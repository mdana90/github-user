package com.dana.githubuser.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val name: String?,
    val followers: Int,
    val following: Int
)