package com.dana.githubuser.feature.userrepository

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.dana.githubuser.common.formatToKorM
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileSection
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileUIState
import org.junit.Rule
import org.junit.Test

class UserProfileSectionTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun userProfileSection_displayProfile() {
        val uiState = UserProfileUIState.Success(
            username = "testuser",
            name = "Test User",
            avatarUrl = "https://example.com/avatar.png",
            followers = 1000,
            following = 500
        )
        composeTestRule.setContent {
            UserProfileSection(uiState = uiState)
        }
        composeTestRule.onNodeWithText(uiState.username).assertIsDisplayed()
        composeTestRule.onNodeWithText(uiState.name!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(formatToKorM(uiState.followers.toLong())).assertIsDisplayed()
        composeTestRule.onNodeWithText(formatToKorM(uiState.following.toLong())).assertIsDisplayed()
    }

    @Test
    fun userProfileSection_nullName() {
        val uiState = UserProfileUIState.Success(
            username = "testuser",
            name = null,
            avatarUrl = "https://example.com/avatar.png",
            followers = 1000,
            following = 500
        )
        composeTestRule.setContent {
            UserProfileSection(uiState = uiState)
        }
        composeTestRule.onNodeWithText(uiState.username).assertIsDisplayed()
        composeTestRule.onNodeWithTag(getString(R.string.profile_name_test_tag)).assertIsNotDisplayed()
        composeTestRule.onNodeWithText(formatToKorM(uiState.followers.toLong())).assertIsDisplayed()
        composeTestRule.onNodeWithText(formatToKorM(uiState.following.toLong())).assertIsDisplayed()
    }

    private fun getString(resId: Int) = composeTestRule.activity.getString(resId)
}