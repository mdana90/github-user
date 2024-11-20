package com.dana.githubuser.feature.userrepository.repositorylist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun RepositoryItem(
    modifier: Modifier = Modifier,
    uiState: RepositoryUIState,
    onClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onClick(uiState.url) }
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        RepositoryName(uiState.name)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            uiState.language?.let {
                ProgrammingLanguage(it)
                Spacer(modifier = Modifier.width(16.dp))
            }
            Stargazer(uiState.formattedStargazersCount)
        }
        uiState.description?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Description(it)
        }
    }
}

@Composable
private fun RepositoryName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun Stargazer(starCount: String) {
    Icon(
        modifier = Modifier.size(16.dp),
        imageVector = Icons.Default.Star,
        contentDescription = "Star icon",
        tint = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.width(2.dp))
    Text(
        text = starCount,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
private fun ProgrammingLanguage(language: String) {
    Text(
        text = language,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
private fun Description(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}