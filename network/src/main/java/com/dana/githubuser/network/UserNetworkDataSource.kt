package com.dana.githubuser.network

import com.dana.githubuser.model.Repository
import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User

/**
 * Interface  for network data source of User.
 */
interface UserNetworkDataSource {

    /**
     * Fetches the user details for the given username.
     *
     * @param username The username of the user to fetch details for.
     * @return A [Result] containing the user details.
     */
    suspend fun getUser(username: String): Result<User>

    /**
     * Fetches the list of repositories for the given username.
     *
     * @param username The username of the user whose repositories are to be fetched.
     * @param page The page number to fetch.
     * @param perPage The number of repositories per page.
     * @return A [Result] containing the list of repositories.
     */
    suspend fun getUserRepositories(
        username: String,
        page: Int,
        perPage: Int
    ): Result<List<Repository>>

    /**
     * Fetches the list of users starting from a specific user ID.
     *
     * @param since The user ID to start fetching from.
     * @param perPage The number of users per page.
     * @return A [Result] containing the list of users.
     */
    suspend fun getUserList(since: Int, perPage: Int): Result<List<User>>
}