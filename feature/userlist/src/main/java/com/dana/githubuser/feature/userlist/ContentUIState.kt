package com.dana.githubuser.feature.userlist

/**
 * Represents the UI state of the content.
 */
sealed interface ContentUIState {
    data class Success(val users: List<UserUIState>) : ContentUIState
    data class Error(val message: String) : ContentUIState
}