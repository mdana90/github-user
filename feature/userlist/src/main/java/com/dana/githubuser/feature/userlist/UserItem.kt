package com.dana.githubuser.feature.userlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dana.github.composables.NetworkImage

/**
 * Display a user item with an avatar and username.
 *
 * @param modifier Modifier for the Row.
 * @param uiState The user item to be displayed.
 * @param onClick Function that is invoked when the user item is clicked.
 */
@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    uiState: UserUIState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NetworkImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentDescription = stringResource(id = R.string.avatar_desc),
            imageUrl = uiState.avatarUrl,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = uiState.username,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
