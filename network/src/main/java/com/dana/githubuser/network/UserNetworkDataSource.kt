package com.dana.githubuser.network

import com.dana.githubuser.model.Repository
import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User

interface UserNetworkDataSource {
    suspend fun getUser(username: String): Result<User>

    suspend fun getUserRepositories(
        username: String,
        page: Int,
        perPage: Int
    ): Result<List<Repository>>

    suspend fun getUserList(since: Int, perPage: Int): Result<List<User>>
}