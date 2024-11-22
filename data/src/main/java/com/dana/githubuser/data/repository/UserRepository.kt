package com.dana.githubuser.data.repository

import com.dana.githubuser.model.Repository
import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User

/**
 * Interface defining the methods for user data.
 */
interface UserRepository {

    /**
     * Fetches the user for the given username.
     *
     * @param username The username of the user to fetch for.
     * @return A [Result] containing the user.
     */
    suspend fun getUser(username: String): Result<User>

    /**
     * Fetches the list of repositories for the given username.
     *
     * @param username The username of the user whose repositories are to be fetched.
     * @param page The page number to fetch.
     * @return A [Result] containing the list of repositories.
     */
    suspend fun getUserRepositories(
        username: String,
        page: Int
    ): Result<List<Repository>>

    /**
     * Fetches the list of users starting from a since.
     *
     * @param since The since to start fetching from.
     * @return A [Result] containing the list of users.
     */
    suspend fun getUserList(since: Int): Result<List<User>>
}