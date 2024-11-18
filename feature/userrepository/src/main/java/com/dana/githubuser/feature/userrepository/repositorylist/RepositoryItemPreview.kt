package com.dana.githubuser.feature.userrepository.repositorylist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun RepositoryItemPreview() {
    val sampleRepository = RepositoryUIState(
        name = "Sample Repository",
        description = "This is a sample repository description.",
        language = "Kotlin",
        url = "https://github.com/android/nowinandroid",
        stargazersCount = 42
    )
    RepositoryItem(uiState = sampleRepository)
}