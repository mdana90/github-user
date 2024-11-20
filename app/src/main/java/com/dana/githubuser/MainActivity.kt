package com.dana.githubuser

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dana.githubuser.feature.userlist.UserListScreen
import com.dana.githubuser.feature.userlist.UserListViewModel
import com.dana.githubuser.feature.userrepository.UserRepositoryArgs
import com.dana.githubuser.feature.userrepository.UserRepositoryScreen
import com.dana.githubuser.feature.userrepository.UserRepositoryViewModel
import com.dana.githubuser.navigation.NavRoutes
import com.dana.githubuser.ui.theme.GithubUserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubUserTheme {
                val context = LocalContext.current
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = NavRoutes.UserList.route) {
                    composable(NavRoutes.UserList.route) {
                        UserListScreen(
                            viewModel = hiltViewModel<UserListViewModel>(),
                            onUserClick = { username ->
                                navController.navigate(NavRoutes.UserRepository.createRoute(username))
                            }
                        )
                    }
                    composable(
                        route = NavRoutes.UserRepository.route,
                        arguments = listOf(navArgument(UserRepositoryArgs.username) { type = NavType.StringType })
                    ) {
                        UserRepositoryScreen(
                            viewModel = hiltViewModel<UserRepositoryViewModel>(),
                            onBackClick = { navController.popBackStack() },
                            onRepositoryClick = { url ->
                                openWebPage(context, url)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun openWebPage(context: Context, url: String) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}