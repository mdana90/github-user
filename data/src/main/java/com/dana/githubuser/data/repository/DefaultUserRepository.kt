package com.dana.githubuser.data.repository

import com.dana.githubuser.model.Repository
import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User
import com.dana.githubuser.network.UserNetworkDataSource
import javax.inject.Inject

internal class DefaultUserRepository @Inject constructor(
    private val userNetworkDataSource: UserNetworkDataSource
) : UserRepository {
    override suspend fun getUser(username: String): Result<User> {
        return userNetworkDataSource.getUser(username)
    }

    override suspend fun getUserRepositories(
        username: String,
        page: Int,
        perPage: Int
    ): Result<List<Repository>> {
        return userNetworkDataSource.getUserRepositories(username, page, perPage)
    }

    override suspend fun getUserList(since: Int): Result<List<User>> {
        return userNetworkDataSource.getUserList(since, PER_PAGE)
    }

    companion object {
        private const val PER_PAGE = 20
    }
}