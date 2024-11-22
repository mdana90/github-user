package com.dana.github.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Displays an error message with a retry button.
 *
 * @param modifier The modifier to be applied to the Box.
 * @param message The error message to be displayed.
 * @param onRetryClicked Function called when the retry button is clicked.
 */
@Composable
fun ErrorView(modifier: Modifier = Modifier, message: String, onRetryClicked: () -> Unit) {
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = message
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onRetryClicked() },
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.button_retry)
            )
        }
    }
}