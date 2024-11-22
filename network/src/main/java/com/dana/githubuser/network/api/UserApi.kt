package com.dana.githubuser.network.api

import com.dana.githubuser.network.UrlConstants
import com.dana.githubuser.network.response.RepositoryResponse
import com.dana.githubuser.network.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface API endpoints for user-related network operations.
 */
interface UserApi {
    @GET(UrlConstants.USER_BY_USERNAME)
    suspend fun getUser(@Path("username") username: String): UserResponse

    @GET(UrlConstants.USER_REPOSITORIES)
    suspend fun getUserRepositories(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<RepositoryResponse>

    @GET(UrlConstants.USER_USER_LIST)
    suspend fun getUserList(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<UserResponse>
}