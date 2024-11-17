package com.dana.githubuser.feature.userrepository

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileSection
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileUIState

@Composable
fun UserRepositoryScreen(viewModel: UserRepositoryViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar() }
    ) { innerPadding ->
        val userUIState = viewModel.userProfileUIState
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (userUIState is UserProfileUIState.Success) {
                UserProfileSection(userUIState)
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.load()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar() {
    TopAppBar(
        modifier = Modifier.shadow(elevation = 4.dp),
        title = {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = R.string.title_profile),
                fontWeight = FontWeight.W500
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(8.dp),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        },
    )
}