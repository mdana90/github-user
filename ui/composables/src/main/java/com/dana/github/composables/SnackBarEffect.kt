package com.dana.github.composables

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Displays a snackbar with a given message.
 *
 * @param message The message to be displayed in the snackbar.
 * @param coroutineScope The coroutine scope to launch the snackbar.
 * @param snackBarHostState The state of the snackbar host.
 * @param onDismissed Function called when the snackbar is dismissed.
 */
@Composable
fun SnackBarEffect(
    message: String?,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onDismissed: () -> Unit
) {
    LaunchedEffect(message) {
        message?.let {
            coroutineScope.launch {
                val result = snackBarHostState.showSnackbar(it)
                if (result == SnackbarResult.Dismissed) {
                    onDismissed()
                }
            }
        }
    }
}