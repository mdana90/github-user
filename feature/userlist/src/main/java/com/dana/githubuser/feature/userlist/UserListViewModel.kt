package com.dana.githubuser.feature.userlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dana.githubuser.common.di.IoDispatcher
import com.dana.githubuser.data.repository.UserRepository
import com.dana.githubuser.model.Result
import com.dana.githubuser.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var contentUIState by mutableStateOf<ContentUIState>(ContentUIState.Success(emptyList()))
        private set
    var isRefreshing by mutableStateOf(false)
        private set
    var isLoadingMore by mutableStateOf(false)
        private set

    var snackBarMessage by mutableStateOf<String?>(null)

    private var since = INITIAL_SINCE
    private var canLoadMore = false

    fun refresh() {
        if (isRefreshing) return

        isRefreshing = true
        since = INITIAL_SINCE
        viewModelScope.launch {
            val result = withContext(dispatcher) {
                userRepository.getUserList(since)
            }

            when (result) {
                is Result.Success -> {
                    contentUIState = ContentUIState.Success(result.data.map(User::toUIState))
                    canLoadMore = result.data.isNotEmpty()
                    if (result.data.isNotEmpty()) {
                        since = result.data.last().id
                    }
                }
                is Result.Error -> {
                    contentUIState.let {
                        if (it is ContentUIState.Success && it.users.isNotEmpty()) {
                            snackBarMessage = result.message
                        } else {
                            contentUIState = ContentUIState.Error(result.message)
                        }
                    }
                }
            }
            isRefreshing = false
        }
    }

    fun loadMore() {
        if (!canLoadMore || isLoadingMore) return
        val uiState = contentUIState as? ContentUIState.Success ?: return

        isLoadingMore = true
        viewModelScope.launch {
            val result = withContext(dispatcher) {
                userRepository.getUserList(since)
            }

            when (result) {
                is Result.Success -> {
                    contentUIState = ContentUIState.Success(
                        uiState.users + result.data.map(User::toUIState)
                    )
                    if (result.data.isNotEmpty()) {
                        since = result.data.last().id
                    }
                    canLoadMore = result.data.isNotEmpty()
                }
                is Result.Error -> {
                    snackBarMessage = result.message
                }
            }
            isLoadingMore = false
        }
    }

    fun onDialogDismissed() {
        snackBarMessage = null
    }

    companion object {
        private const val INITIAL_SINCE = 0
    }
}