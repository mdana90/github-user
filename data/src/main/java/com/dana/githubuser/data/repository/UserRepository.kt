package com.dana.githubuser.data.repository

import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User

interface UserRepository {
    suspend fun getUser(username: String): Result<User>
}