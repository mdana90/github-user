package com.dana.githubuser.feature.userrepository

import com.dana.githubuser.common.formatToKorM
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileUIState
import com.dana.githubuser.feature.userrepository.userprofile.toUIState
import com.dana.githubuser.model.User
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserProfileUIStateTest {

    @Test
    fun `test Success state`() {
        val user = mockk<User>()
        every { user.username } returns "testuser"
        every { user.name } returns "Test User"
        every { user.avatarUrl } returns "http://example.com/avatar.png"
        every { user.followers } returns 1500
        every { user.following } returns 300

        val state: UserProfileUIState = user.toUIState()
        assert(state is UserProfileUIState.Success)
        state as UserProfileUIState.Success

        assertEquals("testuser", state.username)
        assertEquals("Test User", state.name)
        assertEquals("http://example.com/avatar.png", state.avatarUrl)
        assertEquals(1500, state.followers)
        assertEquals(300, state.following)
        assertEquals(formatToKorM(1500), state.formattedFollowers)
        assertEquals(formatToKorM(300), state.formattedFollowing)
    }
}