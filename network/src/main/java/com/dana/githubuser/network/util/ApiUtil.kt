package com.dana.githubuser.network.util

import com.dana.githubuser.model.Result
import retrofit2.HttpException
import java.io.IOException

suspend fun <T : Any> apiCall(apiCall: suspend () -> Result<T>): Result<T> {
    return try {
        apiCall()
    } catch (e: IOException) {
        Result.Error(e, "Failed to fetch data due to network issues")
    } catch (e: HttpException) {
        Result.Error(e, "Server responded with an error")
    } catch (e: Exception) {
        Result.Error(e, "An unknown error occurred")
    }
}