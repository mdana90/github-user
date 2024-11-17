package com.dana.githubuser.model

data class Repository(
    val name: String,
    val stargazersCount: Int,
    val language: String?,
    val description: String?,
    val htmlUrl: String
)