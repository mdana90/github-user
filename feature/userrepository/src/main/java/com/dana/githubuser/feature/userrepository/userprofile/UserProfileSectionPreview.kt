package com.dana.githubuser.feature.userrepository.userprofile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dana.githubuser.model.User


@Preview(showBackground = true)
@Composable
fun PreviewUserProfileSection() {
    val sampleUser = User(
        username = "sampleUser",
        avatarUrl = "https://avatars.githubusercontent.com/u/32689599?s=48&v=4",
        name = "Sample User",
        followers = 2290643,
        following = 50
    )
    val uiState = UserProfileUIState.Success(sampleUser)
    UserProfileSection(uiState = uiState)
}