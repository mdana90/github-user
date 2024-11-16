package com.dana.githubuser.network

import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User

interface UserNetworkDataSource {
    suspend fun getUser(username: String): Result<User>
}