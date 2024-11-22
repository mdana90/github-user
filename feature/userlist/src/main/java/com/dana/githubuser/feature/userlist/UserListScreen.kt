package com.dana.githubuser.feature.userlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UserListViewModel, onUserClick: (String) -> Unit) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = { TopBar() }
    ) { innerPadding ->
        UserListContent(
            modifier = Modifier.padding(innerPadding),
            uiState = viewModel.contentUIState,
            isRefreshing = viewModel.isRefreshing,
            isLoadingMore = viewModel.isLoadingMore,
            onRefresh = viewModel::refresh,
            onLoadMore = viewModel::loadMore,
            onUserClick = onUserClick
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
private fun TopBar() {
    TopAppBar(
        modifier = Modifier.shadow(elevation = 4.dp),
        title = {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = R.string.title_github_user),
                fontWeight = FontWeight.W500
            )
        },
    )
}