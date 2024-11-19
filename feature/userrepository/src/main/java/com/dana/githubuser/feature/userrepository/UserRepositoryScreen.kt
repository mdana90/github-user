package com.dana.githubuser.feature.userrepository

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dana.github.composables.EndlessLazyColumn
import com.dana.github.composables.ErrorView
import com.dana.github.composables.SnackBarEffect
import com.dana.githubuser.feature.userrepository.repositorylist.RepositoryItem
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileSection
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRepositoryScreen(viewModel: UserRepositoryViewModel, onBackClick: () -> Unit) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = { TopBar(onBackClick = onBackClick) }
    ) { innerPadding ->
        val userUIState = viewModel.userProfileUIState
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            isRefreshing = viewModel.isRefreshing,
            onRefresh = viewModel::refresh
        ) {
            if (userUIState is UserProfileUIState.Success) {
                EndlessLazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    items = mutableListOf<@Composable () -> Unit>().apply {
                        add { UserProfileSection(Modifier.padding(16.dp), userUIState) }
                        if (viewModel.showEmptyRepositories) {
                           add {
                               EmptyRepositoriesMessage(userUIState)
                           }
                        } else {
                            addAll(viewModel.repositories.map { { RepositoryItem(uiState = it) } })
                        }
                    } ,
                    divider = { HorizontalDivider(thickness = 0.5.dp) },
                    loadMore = viewModel::loadRepositories,
                    isLoadingMoreData = viewModel.isLoadingRepositories
                )
            } else if (userUIState is UserProfileUIState.Error) {
                ErrorView(
                    modifier = Modifier.fillMaxSize(),
                    message = userUIState.message,
                    onRetryClicked = viewModel::refresh
                )
            }
        }
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
private fun EmptyRepositoriesMessage(userUIState: UserProfileUIState.Success) {
    Box(
        Modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            text = stringResource(
                id = R.string.empty_state_repository,
                userUIState.username
            ),
            style = MaterialTheme.typography.titleMedium,
        )
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