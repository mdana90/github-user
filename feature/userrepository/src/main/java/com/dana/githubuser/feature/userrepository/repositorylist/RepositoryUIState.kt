package com.dana.githubuser.feature.userrepository.repositorylist

import com.dana.githubuser.common.formatToKorM
import com.dana.githubuser.model.Repository

data class RepositoryUIState(
    val name: String,
    val description: String?,
    val language: String?,
    val url: String,
    private val stargazersCount: Int,
) {
    val formattedStargazersCount: String
        get() = formatToKorM(stargazersCount.toLong())
}

internal fun Repository.toUIState() = RepositoryUIState(
    name = name,
    description = description,
    language = language,
    url = htmlUrl,
    stargazersCount = stargazersCount
)