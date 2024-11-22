package com.dana.githubuser.feature.userlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dana.github.composables.EndlessLazyColumn
import com.dana.github.composables.ErrorView

/**
 * Displays the user list with pull-to-refresh and load more functionality.
 *
 * @param uiState The content UI state.
 * @param isRefreshing Whether the content is being refreshed.
 * @param isLoadingMore Whether more content is being loaded.
 * @param onRefresh Called when the content is refreshed.
 * @param onLoadMore Called when more content is loaded.
 * @param onUserClick Called when a user item is clicked.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun UserListContent(
    modifier: Modifier = Modifier,
    uiState: ContentUIState,
    isRefreshing: Boolean = false,
    isLoadingMore: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    onUserClick: (String) -> Unit = {}
) {
    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        when (uiState) {
            is ContentUIState.Success -> {
                UserList(
                    userList = uiState.users,
                    isLoadingMore = isLoadingMore,
                    loadMore = onLoadMore,
                    onUserClick = onUserClick
                )
            }

            is ContentUIState.Error -> {
                ErrorView(
                    modifier = Modifier.align(Alignment.Center),
                    message = uiState.message,
                    onRetryClicked = onRefresh
                )
            }
        }
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
