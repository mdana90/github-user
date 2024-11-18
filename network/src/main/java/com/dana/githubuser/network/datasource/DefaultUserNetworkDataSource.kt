package com.dana.githubuser.network.datasource

import com.dana.githubuser.model.Repository
import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User
import com.dana.githubuser.network.UserNetworkDataSource
import com.dana.githubuser.network.api.UserApi
import com.dana.githubuser.network.mapper.toRepository
import com.dana.githubuser.network.mapper.toUser
import com.dana.githubuser.network.response.RepositoryResponse
import com.dana.githubuser.network.response.UserResponse
import com.dana.githubuser.network.util.apiCall
import javax.inject.Inject

internal class DefaultUserNetworkDataSource @Inject constructor(
    private val userApi: UserApi
) : UserNetworkDataSource {
    override suspend fun getUser(username: String): Result<User> {
        return apiCall { Result.Success(userApi.getUser(username).toUser()) }
    }

    override suspend fun getUserRepositories(
        username: String,
        page: Int,
        perPage: Int
    ): Result<List<Repository>> {
        return apiCall { Result.Success(userApi.getUserRepositories(username, page, perPage)
            .filter { !it.fork }
            .map(RepositoryResponse::toRepository))
        }
    }

    override suspend fun getUserList(since: Int, perPage: Int): Result<List<User>> {
        return apiCall {
            Result.Success(userApi.getUserList(since, perPage).map(UserResponse::toUser))
        }
    }
}
