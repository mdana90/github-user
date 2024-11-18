package com.dana.githubuser.feature.userlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    val sampleUser = UserUIState(
        username = "johndoe",
        avatarUrl = "https://via.placeholder.com/150"
    )
    UserItem(uiState = sampleUser, onClick = {})
}