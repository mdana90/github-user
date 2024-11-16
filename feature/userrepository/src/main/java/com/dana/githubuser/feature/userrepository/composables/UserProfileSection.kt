package com.dana.githubuser.feature.userrepository.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dana.githubuser.feature.userrepository.R
import com.dana.githubuser.feature.userrepository.UserProfileUIState

@Composable
internal fun UserProfileSection(uiState: UserProfileUIState.Success) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = uiState.username,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = uiState.name,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserStatisticSection(uiState)
    }
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