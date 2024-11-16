package com.dana.githubuser.model

data class User(
    val username: String,
    val avatarUrl: String,
    val name: String,
    val followers: Int,
    val following: Int
)