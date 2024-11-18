package com.dana.githubuser.feature.userlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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

    private val _userList = mutableStateListOf<UserUIState>()
    val userList: List<UserUIState> = _userList

    var isRefreshing by mutableStateOf(false)
        private set
    var isLoadingMore by mutableStateOf(false)
        private set

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
                    _userList.clear()
                    _userList.addAll(result.data.map(User::toUIState))
                    canLoadMore = true
                    if (result.data.isNotEmpty()) {
                        since = result.data.last().id
                    }
                }
                is Result.Error -> {
                    println("error: ${result.exception.message}")
                }
            }
            isRefreshing = false
        }
    }

    fun loadMore() {
        if (!canLoadMore || isLoadingMore) return

        isLoadingMore = true
        viewModelScope.launch {
            val result = withContext(dispatcher) {
                userRepository.getUserList(since)
            }

            when (result) {
                is Result.Success -> {
                    println("load: ${result.data.map { it.username }}")
                    _userList.addAll(result.data.map(User::toUIState))
                    if (result.data.isNotEmpty()) {
                        since = result.data.last().id
                    }
                    canLoadMore = result.data.isNotEmpty()
                }
                is Result.Error -> {
                    println("error: ${result.exception.message}")
                }
            }
            isLoadingMore = false
        }
    }

    companion object {
        private const val INITIAL_SINCE = 0
    }
}