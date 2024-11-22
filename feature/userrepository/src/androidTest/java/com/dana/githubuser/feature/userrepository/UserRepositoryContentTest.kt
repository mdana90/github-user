package com.dana.githubuser.feature.userrepository

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dana.githubuser.feature.userrepository.repositorylist.RepositoryUIState
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileUIState
import org.junit.Rule
import org.junit.Test
import com.dana.github.composables.R as RComposable

class UserRepositoryContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun repositoryContent_success() {
        composeTestRule.setContent {
            UserRepositoryContent(
                userUIState = userUIState,
                repositories = repositories
            )
        }

        composeTestRule.onNodeWithText(userUIState.username).assertExists()
        composeTestRule.onNodeWithText(repositories[0].name).assertExists()
        composeTestRule.onNodeWithText(repositories[1].name).assertExists()
    }

    @Test
    fun repositoryContent_error() {
        val errorMessage = "An error occurred"
        composeTestRule.setContent {
            UserRepositoryContent(
                userUIState = UserProfileUIState.Error(errorMessage),
                repositories = emptyList()
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertExists()
        composeTestRule.onNodeWithText(getString(RComposable.string.button_retry)).performClick()
    }

    @Test
    fun repositoryContent_emptyRepositories() {
        composeTestRule.setContent {
            UserRepositoryContent(
                userUIState = userUIState,
                repositories = emptyList(),
                showEmptyRepositories = true
            )
        }

        composeTestRule
            .onNodeWithText(getString(R.string.empty_state_repository, userUIState.username))
            .assertExists()
    }

    private fun getString(resId: Int, vararg formatArgs: Any): String {
        return composeTestRule.activity.getString(resId, *formatArgs)
    }

    companion object {
        val userUIState = UserProfileUIState.Success(
            username = "testuser",
            name = "Test User",
            avatarUrl = "http://example.com/avatar.png",
            followers = 100,
            following = 50
        )

        val repositories = listOf(
            RepositoryUIState(
                name = "TestRepo",
                description = "Test Description",
                language = "Kotlin",
                url = "http://example.com",
                stargazersCount = 1500
            ),
            RepositoryUIState(
                name = "TestRepo2",
                description = "Test Description 2",
                language = "Java",
                url = "http://example.com",
                stargazersCount = 2000
            )
        )
    }
}