package com.dana.githubuser.feature.userrepository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dana.githubuser.common.di.IoDispatcher
import com.dana.githubuser.data.repository.UserRepository
import com.dana.githubuser.feature.userrepository.repositorylist.RepositoryUIState
import com.dana.githubuser.feature.userrepository.repositorylist.toUIState
import com.dana.githubuser.feature.userrepository.userprofile.UserProfileUIState
import com.dana.githubuser.feature.userrepository.userprofile.toUIState
import com.dana.githubuser.model.Repository
import com.dana.githubuser.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserRepositoryViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var userProfileUIState by mutableStateOf<UserProfileUIState>(UserProfileUIState.Loading)
        private set
    var isLoadingRepositories by mutableStateOf(false)
        private set
    var isRefreshing by mutableStateOf(false)
        private set
    var showEmptyRepositories by mutableStateOf(false)
        private set

    var snackBarMessage by mutableStateOf<String?>(null)

    private val _repositories = mutableStateListOf<RepositoryUIState>()
    val repositories: List<RepositoryUIState> = _repositories

    private val username = savedStateHandle[UserRepositoryArgs.username] ?: ""
    private var currentPage = FIRST_PAGE
    private var canLoadRepositories = false

    fun refresh() {
        if (isRefreshing) return

        isRefreshing = true
        viewModelScope.launch {
            val result = withContext(dispatcher) {
                userRepository.getUser(username)
            }
            when (result) {
                is Result.Success -> {
                    userProfileUIState = result.data.toUIState()
                    canLoadRepositories = true
                    loadRepositories()
                }
                is Result.Error -> {
                    userProfileUIState = UserProfileUIState.Error(result.message)
                }
            }
            isRefreshing = false
        }
    }

    fun loadRepositories() {
        if (!canLoadRepositories || isLoadingRepositories) return

        isLoadingRepositories = true
        viewModelScope.launch {
            val result = withContext(dispatcher) {
                userRepository.getUserRepositories(username, currentPage)
            }
            when (result) {
                is Result.Success -> {
                    if (currentPage == FIRST_PAGE) {
                        _repositories.clear()
                        showEmptyRepositories = result.data.isEmpty()
                    }
                    _repositories.addAll(result.data.map(Repository::toUIState))
                    currentPage++
                    canLoadRepositories = result.data.isNotEmpty()
                }
                is Result.Error -> {
                    snackBarMessage = result.message
                }
            }
            isLoadingRepositories = false
        }
    }

    fun onDialogDismissed() {
        snackBarMessage = null
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}