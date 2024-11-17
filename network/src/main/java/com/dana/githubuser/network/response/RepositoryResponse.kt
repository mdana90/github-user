package com.dana.githubuser.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryResponse(
    val name: String,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    val language: String?,
    val description: String?,
    @SerialName("html_url")
    val htmlUrl: String,
    val fork: Boolean
)