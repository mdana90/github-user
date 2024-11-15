package com.dana.githubuser.network.api

import com.dana.githubuser.network.UrlConstants
import com.dana.githubuser.network.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET(UrlConstants.USER_BY_USERNAME)
    suspend fun getUser(@Path("username") username: String): UserResponse
}