package com.dana.githubuser.feature.userrepository

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dana.github.composables.SnackBarEffect

/**
 * Display the user repository screen.
 *
 * @param viewModel The ViewModel that provides the UI state and actions.
 * @param onBackClick Called when the back button is clicked.
 * @param onRepositoryClick Called when a repository item is clicked.
 */
@Composable
fun UserRepositoryScreen(
    viewModel: UserRepositoryViewModel,
    onBackClick: () -> Unit,
    onRepositoryClick: (String) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = { TopBar(onBackClick = onBackClick) }
    ) { innerPadding ->
        val userUIState = viewModel.userProfileUIState
        UserRepositoryContent(
            modifier = Modifier.padding(innerPadding),
            userUIState = userUIState,
            repositories = viewModel.repositories,
            isRefreshing = viewModel.isRefreshing,
            isLoadingMore = viewModel.isLoadingRepositories,
            showEmptyRepositories = viewModel.showEmptyRepositories,
            onRefresh = viewModel::refresh,
            onLoadMore = viewModel::loadRepositories,
            onRepositoryClick = onRepositoryClick
        )
    }

    SnackBarEffect(
        message = viewModel.snackBarMessage,
        coroutineScope = coroutineScope,
        snackBarHostState = snackBarHostState,
        onDismissed = viewModel::onDialogDismissed
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.refresh()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(onBackClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.shadow(elevation = 4.dp),
        title = {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = R.string.title_repository),
                fontWeight = FontWeight.W500
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onBackClick() },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        },
    )
}