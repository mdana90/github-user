package com.dana.githubuser.feature.userrepository

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun UserRepositoryScreen(modifier: Modifier = Modifier, viewModel: UserRepositoryViewModel) {
    Box(modifier = modifier) {
        Text(text = "Hello Profile")
        LaunchedEffect(key1 = Unit) {
            viewModel.load()
        }
    }
}