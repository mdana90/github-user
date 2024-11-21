package com.dana.githubuser.network

import com.dana.githubuser.model.Result
import com.dana.githubuser.network.api.UserApi
import com.dana.githubuser.network.datasource.DefaultUserNetworkDataSource
import com.dana.githubuser.network.mapper.toRepository
import com.dana.githubuser.network.mapper.toUser
import com.dana.githubuser.network.response.RepositoryResponse
import com.dana.githubuser.network.response.UserResponse
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DefaultUserNetworkDataSourceTest {

    @MockK
    private lateinit var userApi: UserApi
    private lateinit var defaultUserNetworkDataSource: DefaultUserNetworkDataSource

    @BeforeEach
    fun setUp() {
        defaultUserNetworkDataSource = DefaultUserNetworkDataSource(userApi)
    }

    @Test
    fun `getUser when api success should returns user`() = runTest {
        val username = "testuser"
        val userResponse = userResponse1
        coEvery { userApi.getUser(username) } returns userResponse

        val result = defaultUserNetworkDataSource.getUser(username)

        assertEquals(userResponse.toUser(), (result as Result.Success).data)
    }

    @Test
    fun `getUserRepositories when api success should returns non forked repository list`() = runTest {
        val username = "user1"
        coEvery { userApi.getUserRepositories(username, 1, 10) } returns repositoryResponseList

        val result = defaultUserNetworkDataSource.getUserRepositories(username, 1, 10)

        assertEquals(
            nonForkedRepositoryResponseList.map(RepositoryResponse::toRepository),
            (result as Result.Success).data
        )
    }

    @Test
    fun `getUserList when api success should returns user list`() = runTest {
        coEvery { userApi.getUserList(0, 10) } returns userResponseList

        val result = defaultUserNetworkDataSource.getUserList(0, 10)

        assertEquals(userResponseList.map(UserResponse::toUser), (result as Result.Success).data)
    }

    companion object {
        val userResponse1 = UserResponse(1, "user1", "avatarUrl1", "User One", 10, 5)
        private val userResponse2 = UserResponse(2, "user2", "avatarUrl2", "User Two", 20, 15)
        val userResponseList = listOf(userResponse1, userResponse2)

        private val repositoryResponse1 = RepositoryResponse(
            name = "Repo1",
            stargazersCount = 10,
            language = "Kotlin",
            description = "Description",
            htmlUrl = "",
            fork = false
        )
        private val repositoryResponse2 = RepositoryResponse(
            name = "Repo2",
            stargazersCount = 20,
            language = "Java",
            description = "Description",
            htmlUrl = "",
            fork = true
        )
        val repositoryResponseList = listOf(repositoryResponse1, repositoryResponse2)
        val nonForkedRepositoryResponseList = repositoryResponseList.filter { !it.fork }
    }
}