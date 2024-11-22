package com.dana.githubuser.feature.userrepository

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dana.githubuser.feature.userrepository.repositorylist.RepositoryItem
import com.dana.githubuser.feature.userrepository.repositorylist.RepositoryUIState
import org.junit.Rule
import org.junit.Test

class RepositoryItemTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testRepositoryItem_displaysCorrectly() {
        composeTestRule.setContent {
            RepositoryItem(uiState = uiState, onClick = {})
        }

        composeTestRule.onNodeWithText(uiState.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(uiState.description!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(uiState.language!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(uiState.formattedStargazersCount).assertIsDisplayed()
    }

    @Test
    fun testRepositoryItem_nullDesc_nullLang() {
        val uiState = RepositoryUIState(
            name = "TestRepo",
            description = null,
            language = null,
            url = "http://example.com",
            stargazersCount = 1500
        )
        composeTestRule.setContent {
            RepositoryItem(uiState = uiState, onClick = {})
        }

        composeTestRule.onNodeWithText(uiState.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(getString(R.string.repository_lang_test_tag)).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(getString(R.string.repository_description_test_tag)).assertIsNotDisplayed()
        composeTestRule.onNodeWithText(uiState.formattedStargazersCount).assertIsDisplayed()
    }

    @Test
    fun testRepositoryItemClick() {

        var clickedUrl: String? = null

        composeTestRule.setContent {
            RepositoryItem(uiState = uiState, onClick = { url -> clickedUrl = url })
        }

        composeTestRule.onNodeWithText("TestRepo").performClick()
        assert(clickedUrl == "http://example.com")
    }

    private fun getString(resId: Int) = composeTestRule.activity.getString(resId)

    companion object  {
        val uiState = RepositoryUIState(
            name = "TestRepo",
            description = "Test Description",
            language = "Kotlin",
            url = "http://example.com",
            stargazersCount = 1500
        )
    }
}