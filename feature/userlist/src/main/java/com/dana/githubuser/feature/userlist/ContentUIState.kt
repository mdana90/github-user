package com.dana.githubuser.feature.userlist

sealed interface ContentUIState {
    data class Success(val users: List<UserUIState>) : ContentUIState
    data class Error(val message: String) : ContentUIState
}