package com.dana.githubuser.feature.userlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.dana.github.composables.EndlessLazyColumn
import com.dana.github.composables.ErrorView

@Composable
fun UserListScreen(viewModel: UserListViewModel, onUserClick: (String) -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
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