package com.dana.githubuser.feature.userrepository

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dana.githubuser.feature.userrepository.repositorylist.RepositoryUIState
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileUIState

@Preview(showBackground = true)
@Composable
fun PreviewUserRepositoryContentSuccess() {
    val userSuccessState = UserProfileUIState.Success(
        username = "testuser",
        avatarUrl = "https://example.com/avatar.png",
        name = "Test User",
        followers = 100,
        following = 50
    )

    val repositoryList = listOf(
        RepositoryUIState(
            name = "Repo1",
            description = "Description 1",
            language = "Kotlin",
            url = "https://example.com/repo1",
            stargazersCount = 1500
        ),
        RepositoryUIState(
            name = "Repo2",
            description = "Description 2",
            language = "Java",
            url = "https://example.com/repo2",
            stargazersCount = 2500
        )
    )

    UserRepositoryContent(
        userUIState = userSuccessState,
        repositories = repositoryList
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewUserRepositoryContentEmpty() {
    val userSuccessState = UserProfileUIState.Success(
        username = "testuser",
        avatarUrl = "https://example.com/avatar.png",
        name = "Test User",
        followers = 100,
        following = 50
    )

    UserRepositoryContent(
        userUIState = userSuccessState,
        repositories = emptyList(),
        showEmptyRepositories = true,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewUserRepositoryContentError() {
    val userErrorState = UserProfileUIState.Error(message = "Failed to load user profile")

    UserRepositoryContent(
        userUIState = userErrorState,
        repositories = emptyList()
    )
}