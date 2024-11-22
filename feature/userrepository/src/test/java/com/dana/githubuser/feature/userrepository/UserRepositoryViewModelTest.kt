package com.dana.githubuser.feature.userrepository

import androidx.lifecycle.SavedStateHandle
import com.dana.githubuser.common_test.extension.CoroutineTestExtension
import com.dana.githubuser.data.repository.UserRepository
import com.dana.githubuser.feature.userrepository.repositorylist.RepositoryUIState
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileUIState
import com.dana.githubuser.model.Repository
import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class, CoroutineTestExtension::class)
class UserRepositoryViewModelTest {

    private lateinit var viewModel: UserRepositoryViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        savedStateHandle = SavedStateHandle(mapOf(UserRepositoryArgs.username to "testuser"))
        viewModel = UserRepositoryViewModel(userRepository, UnconfinedTestDispatcher(), savedStateHandle)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `refresh should update userProfileUIState with Success on successful user data retrieval`() = runTest {
        coEvery { userRepository.getUser("testuser") } returns Result.Success(user)
        coEvery { userRepository.getUserRepositories("testuser", 1) } returns Result.Success(repositoryList)

        viewModel.refresh()

        assertTrue(viewModel.userProfileUIState is UserProfileUIState.Success)
        val successState = viewModel.userProfileUIState as UserProfileUIState.Success
        assertEquals(user.username, successState.username)
        assertEquals(user.name, successState.name)
        assertEquals(user.avatarUrl, successState.avatarUrl)
        assertEquals(user.followers, successState.followers)
        assertEquals(user.following, successState.following)
    }

    @Test
    fun `refresh should update userProfileUIState with Error on user data retrieval failure`() = runTest {
        coEvery { userRepository.getUser("testuser") } returns Result.Error(error)

        viewModel.refresh()

        assertTrue(viewModel.userProfileUIState is UserProfileUIState.Error)
        val errorState = viewModel.userProfileUIState as UserProfileUIState.Error
        assertEquals("Error message", errorState.message)
    }

    @Test
    fun `loadRepositories should update repositories on successful repository data retrieval`() = runTest {
        coEvery { userRepository.getUserRepositories("testuser", 1) } returns Result.Success(repositoryList)
        coEvery { userRepository.getUserRepositories("testuser", 2) } returns Result.Success(repositoryList2)
        coEvery { userRepository.getUser("testuser") } returns Result.Success(user)
        viewModel.refresh()

        viewModel.loadRepositories()

        assertEquals(repositoryList.size + repositoryList2.size, viewModel.repositories.size)
        assertRepositoryWithUIState(repositoryList[0], viewModel.repositories[0])
        assertRepositoryWithUIState(repositoryList[1], viewModel.repositories[1])
        assertRepositoryWithUIState(repositoryList2[0], viewModel.repositories[2])
        assertRepositoryWithUIState(repositoryList2[1], viewModel.repositories[3])
    }

    @Test
    fun `loadRepositories should show error message on repository data retrieval failure`() = runTest {
        coEvery { userRepository.getUserRepositories("testuser", 1) } returns Result.Error(error)
        coEvery { userRepository.getUser("testuser") } returns Result.Success(user)
        viewModel.refresh()

        viewModel.loadRepositories()

        assertEquals(error.message, viewModel.snackBarMessage)
    }

    @Test
    fun `onDialogDismissed should clear snackBarMessage`() {
        viewModel.snackBarMessage = error.message

        viewModel.onDialogDismissed()

        assertEquals(null, viewModel.snackBarMessage)
    }

    private fun assertRepositoryWithUIState(repository: Repository, uiState: RepositoryUIState) {
        assertEquals(repository.name, uiState.name)
        assertEquals(repository.language, uiState.language)
        assertEquals(repository.description, uiState.description)
        assertEquals(repository.htmlUrl, uiState.url)
    }

    companion object {
        val error = Exception("Error message")
        val user = User(1, "testuser", "avatarUrl1", "User One", 10, 5)

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
        private val repository3 = Repository(
            name = "Repo3",
            stargazersCount = 30,
            language = "JavaScript",
            description = "Description",
            htmlUrl = ""
        )
        private val repository4 = Repository(
            name = "Repo4",
            stargazersCount = 40,
            language = "Python",
            description = "Description",
            htmlUrl = ""
        )
        val repositoryList = listOf(repository, repository2)
        val repositoryList2 = listOf(repository3, repository4)
    }
}