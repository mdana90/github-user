package com.dana.githubuser.feature.userlist

/**
 * Represents the UI state of the user list content.
 */
sealed interface UserListContentUIState {
    data class Success(val users: List<UserUIState>) : UserListContentUIState
    data class Error(val message: String) : UserListContentUIState
}