package com.dana.githubuser.feature.userlist

import com.dana.githubuser.common_test.extension.CoroutineTestExtension
import com.dana.githubuser.data.repository.UserRepository
import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class, CoroutineTestExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: UserListViewModel

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        viewModel = UserListViewModel(userRepository, UnconfinedTestDispatcher())
    }

    @Test
    fun `refresh success then contentUIState should be success`() = runTest {
        coEvery { userRepository.getUserList(0) } returns Result.Success(initialUsers)

        viewModel.refresh()

        assertTrue(viewModel.contentUIState is ContentUIState.Success)
        assertIterableEquals(
            initialUsers.map(User::toUIState),
            (viewModel.contentUIState as ContentUIState.Success).users
        )
        assertFalse(viewModel.isRefreshing)
        coVerify { userRepository.getUserList(0) }
    }

    @Test
    fun `refresh error when list not empty then should update snackBarMessage on error`() = runTest {
        coEvery { userRepository.getUserList(0) } returns Result.Success(initialUsers) andThen Result.Error(error)
        viewModel.refresh()

        viewModel.refresh()

        assertTrue(viewModel.contentUIState is ContentUIState.Success)
        assertIterableEquals(
            initialUsers.map(User::toUIState),
            (viewModel.contentUIState as ContentUIState.Success).users
        )
        assertFalse(viewModel.isRefreshing)
        coVerify(exactly = 2) { userRepository.getUserList(0) }
    }


    @Test
    fun `refresh error then contentUIState should be error`() = runTest {
        coEvery { userRepository.getUserList(0) } returns Result.Error(error)

        viewModel.refresh()

        assertTrue(viewModel.contentUIState is ContentUIState.Error)
        assertEquals(error.message, (viewModel.contentUIState as ContentUIState.Error).message)
        assertFalse(viewModel.isRefreshing)
        coVerify { userRepository.getUserList(0) }
    }

    @Test
    fun `loadMore success should append users to contentUIState`() = runTest {
        coEvery { userRepository.getUserList(0) } returns Result.Success(initialUsers)
        coEvery { userRepository.getUserList(1) } returns Result.Success(moreUsers)
        viewModel.refresh()

        viewModel.loadMore()

        assertTrue(viewModel.contentUIState is ContentUIState.Success)
        assertIterableEquals(
            initialUsers.map(User::toUIState) + moreUsers.map(User::toUIState),
            (viewModel.contentUIState as ContentUIState.Success).users
        )
        assertFalse(viewModel.isLoadingMore)
        coVerify { userRepository.getUserList(1) }
    }

    @Test
    fun `loadMore should not executed when list empty`() = runTest {
        coEvery { userRepository.getUserList(0) } returns Result.Success(emptyList())
        viewModel.refresh()

        viewModel.loadMore()

        coVerify(exactly = 0) { userRepository.getUserList(1) }
    }

    @Test
    fun `loadMore error then should update snackBarMessage on error `() = runTest {
        coEvery { userRepository.getUserList(0) } returns Result.Success(initialUsers)
        coEvery { userRepository.getUserList(1) } returns Result.Error(error)
        viewModel.refresh()

        viewModel.loadMore()

        assertEquals(error.message, viewModel.snackBarMessage)
        assertFalse(viewModel.isLoadingMore)
        coVerify { userRepository.getUserList(1) }
    }

    @Test
    fun `onDialogDismissed should clear snackBarMessage`() {
        viewModel.snackBarMessage = "Error occurred"

        viewModel.onDialogDismissed()

        assertEquals(null, viewModel.snackBarMessage)
    }

    companion object {
        private val error = Exception("Error occurred")

        private val initialUsers = listOf(User(1, "username", "avatarUrl", "name", 10, 5))
        private val moreUsers = listOf(User(2, "username2", "avatarUrl2", "name2", 20, 10))
    }
}