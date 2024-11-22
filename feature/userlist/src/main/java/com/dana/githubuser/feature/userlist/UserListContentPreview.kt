package com.dana.githubuser.feature.userlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun PreviewUserListContentSuccess() {
    val successState = UserListContentUIState.Success(
        users = listOf(
            UserUIState("User 1", "https://example.com/avatar1.png"),
            UserUIState( "User 2", "https://example.com/avatar2.png")
        )
    )

    UserListContent(uiState = successState)
}

@Preview(showBackground = true)
@Composable
fun PreviewUserListContentError() {
    val errorState = UserListContentUIState.Error(message = "Failed to load user list")

    UserListContent(uiState = errorState)
}