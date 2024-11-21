package com.dana.githubuser.data

import com.dana.githubuser.data.repository.DefaultUserRepository
import com.dana.githubuser.model.Repository
import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User
import com.dana.githubuser.network.UserNetworkDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultUserRepositoryTest {

    private lateinit var userNetworkDataSource: UserNetworkDataSource
    private lateinit var defaultUserRepository: DefaultUserRepository

    @BeforeEach
    fun setUp() {
        userNetworkDataSource = mockk()
        defaultUserRepository = DefaultUserRepository(userNetworkDataSource)
    }

    @Test
    fun `getUser should return user`() = runTest {
        val username = "testuser"
        coEvery { userNetworkDataSource.getUser(username) } returns Result.Success(user)

        val result = defaultUserRepository.getUser(username)

        assertEquals(user, (result as Result.Success).data)
    }

    @Test
    fun `getUserRepositories should return list of repositories`() = runTest {
        val username = "testuser"
        coEvery {
            userNetworkDataSource.getUserRepositories(username, 1, 20)
        } returns Result.Success(repositoryList)

        val result = defaultUserRepository.getUserRepositories(username, 1)

        assertIterableEquals(repositoryList, (result as Result.Success).data)
    }

    @Test
    fun `getUserList should return list of users`() = runTest {
        val since = 0
        coEvery { userNetworkDataSource.getUserList(since, 20) } returns Result.Success(userList)

        val result = defaultUserRepository.getUserList(since)

        assertIterableEquals(userList, (result as Result.Success).data)
    }


    companion object {
        val user = User(1, "user1", "avatarUrl1", "User One", 10, 5)
        private val user2 = User(2, "user2", "avatarUrl2", "User Two", 20, 15)
        val userList = listOf(user, user2)

        private val repository = Repository(
            name = "Repo1",
            stargazersCount = 10,
            language = "Kotlin",
            description = "Description",
            htmlUrl = ""
        )
        private val repository2 = Repository(
            name = "Repo2",
            stargazersCount = 20,
            language = "Java",
            description = "Description",
            htmlUrl = ""
        )
        val repositoryList = listOf(repository, repository2)

    }
}