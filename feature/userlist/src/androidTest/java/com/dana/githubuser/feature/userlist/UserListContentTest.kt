package com.dana.githubuser.feature.userlist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class UserListContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun userListContent_success() {
        composeTestRule.setContent {
            UserListContent(uiState = ContentUIState.Success(initialUsers))
        }

        composeTestRule.onNodeWithText("username").assertIsDisplayed()
    }

    @Test
    fun userListContent_error() {
        composeTestRule.setContent {
            UserListContent(uiState = ContentUIState.Error(errorMessage))
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    companion object {
        private val initialUsers = listOf(
            UserUIState("username", "avatarUrl"),
            UserUIState("username2", "avatarUrl2"),
        )
        private val errorMessage = "Error occurred"
    }
}