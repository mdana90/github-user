package com.dana.githubuser.feature.userrepository.userprofile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dana.github.composables.NetworkImage
import com.dana.githubuser.feature.userrepository.R

/**
 * Display the user profile section.
 *
 * @param modifier Modifier for the layout.
 * @param uiState The UI state containing user profile information.
 */
@Composable
internal fun UserProfileSection(modifier: Modifier = Modifier, uiState: UserProfileUIState.Success) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        UserAvatar(uiState.avatarUrl)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = uiState.username,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.titleLarge
        )
        uiState.name?.let {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                modifier = Modifier.testTag(stringResource(R.string.profile_name_test_tag)),
                text = it,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        UserStatisticSection(uiState)
    }
}

@Composable
private fun UserAvatar(avatarUrl: String) {
    NetworkImage(
        modifier = Modifier
            .size(76.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), CircleShape)
            .clip(CircleShape),
        imageUrl = avatarUrl,
    )
}

@Composable
private fun UserStatisticSection(uiState: UserProfileUIState.Success) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        UserStatisticInfo(
            amount = uiState.formattedFollowers,
            label = pluralStringResource(
                id = R.plurals.label_followers,
                count = uiState.followers
            )
        )
        UserStatisticInfo(
            amount = uiState.formattedFollowing,
            label = pluralStringResource(
                id = R.plurals.label_followings,
                count = uiState.following
            )
        )
    }
}

@Composable
private fun UserStatisticInfo(amount: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = amount,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}