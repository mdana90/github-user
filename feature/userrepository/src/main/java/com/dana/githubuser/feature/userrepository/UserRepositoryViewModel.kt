package com.dana.githubuser.feature.userrepository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dana.githubuser.data.repository.UserRepository
import com.dana.githubuser.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserRepositoryViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var userProfileUIState by mutableStateOf<UserProfileUIState>(UserProfileUIState.Loading)
        private set

    fun load() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                userRepository.getUser("mojombo")
            }

            when (result) {
                is Result.Success -> {
                    userProfileUIState = UserProfileUIState.Success(result.data)
                    println("username: ${result.data.username}")
                }
                is Result.Error -> {
                    println("error: ${result.exception}")
                }
            }
        }
    }
}