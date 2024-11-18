package com.dana.githubuser.data.repository

import com.dana.githubuser.model.Repository
import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User

interface UserRepository {
    suspend fun getUser(username: String): Result<User>
    suspend fun getUserRepositories(
        username: String,
        page: Int
    ): Result<List<Repository>>

    suspend fun getUserList(since: Int): Result<List<User>>
}