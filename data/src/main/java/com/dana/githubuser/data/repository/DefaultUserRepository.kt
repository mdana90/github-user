package com.dana.githubuser.data.repository

import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User
import com.dana.githubuser.network.UserNetworkDataSource

internal class DefaultUserRepository(
    private val userNetworkDataSource: UserNetworkDataSource
) : UserRepository {
    override suspend fun getUser(username: String): Result<User> {
        return userNetworkDataSource.getUser(username)
    }
}