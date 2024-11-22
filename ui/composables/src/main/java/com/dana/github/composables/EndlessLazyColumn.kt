package com.dana.github.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier

/**
 * Displays a lazy column with endless scrolling capability.
 *
 * @param modifier Modifier for the LazyColumn.
 * @param listState State to control or observe the list's state.
 * @param loadMore Function to be called when load more.
 * @param isLoadingMoreData Indicating whether more data is currently being loaded.
 * @param divider Divider between items.
 * @param items Items to be displayed in the list.
 */
@Composable
fun EndlessLazyColumn(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    loadMore: () -> Unit = {},
    isLoadingMoreData: Boolean = false,
    divider: @Composable () -> Unit = {},
    items: List<@Composable () -> Unit>
) {
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(items) { index, item ->
            item()
            if (index < items.lastIndex) {
                divider()
            }
        }
        if (isLoadingMoreData) {
            item {
                LoadMoreFooter()
            }
        }
    }
    listState.OnBottomReached(loadMore)
}

@Composable
private fun LazyListState.OnBottomReached(loadMore: () -> Unit) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                if (it) loadMore()
            }
    }
}