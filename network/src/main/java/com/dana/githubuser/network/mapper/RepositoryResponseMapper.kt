package com.dana.githubuser.network.mapper

import com.dana.githubuser.model.Repository
import com.dana.githubuser.network.response.RepositoryResponse

fun RepositoryResponse.toRepository() : Repository {
    return Repository(
        name = name,
        stargazersCount = stargazersCount,
        language = language,
        description = description,
        htmlUrl = htmlUrl
    )
}