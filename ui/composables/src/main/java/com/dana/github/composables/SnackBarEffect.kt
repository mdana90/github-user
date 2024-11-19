package com.dana.github.composables

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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