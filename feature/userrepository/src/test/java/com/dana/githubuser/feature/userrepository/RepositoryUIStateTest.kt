package com.dana.githubuser.feature.userrepository

import com.dana.githubuser.feature.userrepository.repositorylist.RepositoryUIState
import com.dana.githubuser.feature.userrepository.repositorylist.toUIState
import com.dana.githubuser.model.Repository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RepositoryUIStateTest {

    @Test
    fun `test Success state`() {
        val repository = mockk<Repository>()
        every { repository.name } returns "TestRepo"
        every { repository.description } returns "Test Description"
        every { repository.language } returns "Kotlin"
        every { repository.htmlUrl } returns "http://example.com"
        every { repository.stargazersCount } returns 1500

        val uiState: RepositoryUIState = repository.toUIState()

        assertEquals("TestRepo", uiState.name)
        assertEquals("Test Description", uiState.description)
        assertEquals("Kotlin", uiState.language)
        assertEquals("http://example.com", uiState.url)
        assertEquals("1.5K", uiState.formattedStargazersCount)
    }
}