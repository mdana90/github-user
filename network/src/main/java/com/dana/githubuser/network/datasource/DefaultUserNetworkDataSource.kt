package com.dana.githubuser.network.datasource

import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User
import com.dana.githubuser.network.UserNetworkDataSource
import com.dana.githubuser.network.api.UserApi
import com.dana.githubuser.network.mapper.toUser
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class DefaultUserNetworkDataSource @Inject constructor(
    private val userApi: UserApi
) : UserNetworkDataSource {
    override suspend fun getUser(username: String): Result<User> {
        return try {
            Result.Success(userApi.getUser(username).toUser())
        } catch (e: IOException) {
            Result.Error(e, "Failed to fetch data due to network issues")
        } catch (e: HttpException) {
            Result.Error(e, "Server responded with an error")
        } catch (e: Exception) {
            Result.Error(e, "An unknown error occurred")
        }
    }
}
