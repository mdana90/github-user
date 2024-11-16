package com.dana.githubuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dana.githubuser.feature.userrepository.UserRepositoryScreen
import com.dana.githubuser.feature.userrepository.UserRepositoryViewModel
import com.dana.githubuser.ui.theme.GithubUserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubUserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserRepositoryScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = hiltViewModel<UserRepositoryViewModel>()
                    )
                }
            }
        }
    }
}