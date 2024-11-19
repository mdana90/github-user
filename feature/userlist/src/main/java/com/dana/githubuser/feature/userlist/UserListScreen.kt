package com.dana.githubuser.feature.userlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import com.dana.github.composables.EndlessLazyColumn
import com.dana.github.composables.ErrorView
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
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            isRefreshing = viewModel.isRefreshing,
            onRefresh = viewModel::refresh
        ) {
            when (val contentUIState = viewModel.contentUIState) {
                is ContentUIState.Success -> {
                    UserList(
                        userList = contentUIState.users,
                        isLoadingMore = viewModel.isLoadingMore,
                        loadMore = viewModel::loadMore,
                        onUserClick = onUserClick
                    )
                }
                is ContentUIState.Error -> {
                    ErrorView(
                        modifier = Modifier.align(Alignment.Center),
                        message = contentUIState.message,
                        onRetryClicked = viewModel::refresh
                    )
                }
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
private fun UserList(
    userList: List<UserUIState>,
    isLoadingMore: Boolean,
    loadMore: () -> Unit,
    onUserClick: (String) -> Unit
) {
    EndlessLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        items = userList.map {
            { UserItem(uiState = it, onClick = { onUserClick(it.username) }) }
        },
        divider = { HorizontalDivider(thickness = 0.5.dp) },
        loadMore = loadMore,
        isLoadingMoreData = isLoadingMore
    )
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