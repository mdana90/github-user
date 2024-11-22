package com.dana.githubuser.feature.userrepository

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dana.github.composables.EndlessLazyColumn
import com.dana.github.composables.ErrorView
import com.dana.githubuser.feature.userrepository.repositorylist.RepositoryItem
import com.dana.githubuser.feature.userrepository.repositorylist.RepositoryUIState
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileSection
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileUIState

/**
 * Display the user repository content.
 *
 * @param modifier Modifier for the layout.
 * @param userUIState The current UI state of the user profile.
 * @param repositories The list of repository UI states.
 * @param isRefreshing Whether the content is being refreshed.
 * @param isLoadingMore Whether more content is being loaded.
 * @param showEmptyRepositories Whether to show a message when there are no repositories.
 * @param onRefresh Called when the content is refreshed.
 * @param onLoadMore Called when more content is loaded.
 * @param onRepositoryClick Called when a repository item is clicked.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun UserRepositoryContent(
    modifier: Modifier = Modifier,
    userUIState: UserProfileUIState,
    repositories: List<RepositoryUIState>,
    isRefreshing: Boolean = false,
    isLoadingMore: Boolean = false,
    showEmptyRepositories: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    onRepositoryClick: (String) -> Unit = {},
) {
    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        if (userUIState is UserProfileUIState.Success) {
            EndlessLazyColumn(
                modifier = Modifier.fillMaxSize(),
                items = mutableListOf<@Composable () -> Unit>().apply {
                    add { UserProfileSection(Modifier.padding(16.dp), userUIState) }
                    if (showEmptyRepositories) {
                        add { EmptyRepositoriesMessage(userUIState) }
                    } else {
                        addAll(repositories.map {
                            { RepositoryItem(uiState = it, onClick = onRepositoryClick) }
                        })
                    }
                },
                divider = { HorizontalDivider(thickness = 0.5.dp) },
                loadMore = onLoadMore,
                isLoadingMoreData = isLoadingMore
            )
        } else if (userUIState is UserProfileUIState.Error) {
            ErrorView(
                modifier = Modifier.fillMaxSize(),
                message = userUIState.message,
                onRetryClicked = onRefresh
            )
        }
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
