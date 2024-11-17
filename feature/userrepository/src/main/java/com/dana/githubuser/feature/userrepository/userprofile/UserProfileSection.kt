package com.dana.githubuser.feature.userrepository.userprofile

import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dana.github.composables.NetworkImage
import com.dana.githubuser.feature.userrepository.R

@Composable
internal fun UserProfileSection(uiState: UserProfileUIState.Success) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        UserAvatar(uiState.avatarUrl)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = uiState.username,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyLarge
        )
        uiState.name?.let {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
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
            .size(60.dp)
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
        UserStatisticInfo(amount = uiState.followers, label = R.string.label_followers)
        UserStatisticInfo(amount = uiState.following, label = R.string.label_followings)
    }
}

@Composable
private fun UserStatisticInfo(amount: Int, @StringRes label: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = amount.toString(),
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(label),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
    }
}