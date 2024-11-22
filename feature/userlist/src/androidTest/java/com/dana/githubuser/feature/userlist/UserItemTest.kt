package com.dana.githubuser.feature.userlist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class UserItemTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun userItem_displaysCorrectUsernameAndAvatar() {
        composeTestRule.setContent {
            UserItem(
                uiState = userUIState,
                onClick = {}
            )
        }

        composeTestRule.onNodeWithText(userUIState.username).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(getString(R.string.avatar_desc)).assertIsDisplayed()
    }

    @Test
    fun userItem_onClick() {
        var clicked = false

        composeTestRule.setContent {
            UserItem(
                uiState = userUIState,
                onClick = { clicked = true }
            )
        }

        composeTestRule.onNodeWithText(userUIState.username).performClick()
        assert(clicked)
    }

    private fun getString(resId: Int) = composeTestRule.activity.getString(resId)

    companion object {
        val userUIState = UserUIState(
            username = "testuser",
            avatarUrl = "https://example.com/avatar.png"
        )
    }
}