package com.dana.githubuser.network.util

import com.dana.githubuser.model.Result
import retrofit2.HttpException
import java.io.IOException

/**
 * Makes a network API call and handles exceptions.
 *
 * @param T The type of the result expected from the API call.
 * @param apiCall A suspend function representing the API call to be made.
 * @return A [Result] object containing either the successful result or an error.
 */
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