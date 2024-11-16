package com.dana.githubuser.feature.userrepository

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
    fun load() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                userRepository.getUser("mojombo")
            }

            when (result) {
                is Result.Success -> {
                    println("username: ${result.data.username}")
                }
                is Result.Error -> {
                    println("error: ${result.exception}")
                }
            }
        }
    }
}