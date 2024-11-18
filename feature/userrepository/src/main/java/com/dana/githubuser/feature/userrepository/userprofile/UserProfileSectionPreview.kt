package com.dana.githubuser.feature.userrepository.userprofile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun PreviewUserProfileSection() {
    val uiState = UserProfileUIState.Success(
        username = "mojombo",
        name = "Tom Preston-Werner",
        avatarUrl = "https://avatars.githubusercontent.com/u/32689599?s=48&v=4",
        followers = 2290643,
        following = 1
    )
    UserProfileSection(uiState = uiState)
}