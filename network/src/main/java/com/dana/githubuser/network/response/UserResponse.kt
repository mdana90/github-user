package com.dana.githubuser.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val name: String? = null,
    val followers: Int = 0,
    val following: Int = 0
)