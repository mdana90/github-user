package com.dana.githubuser.navigation

import com.dana.githubuser.feature.userrepository.UserRepositoryArgs

sealed class NavRoutes(val route: String) {
    data object UserList : NavRoutes("user_list")
    data object UserRepository : NavRoutes("user_repository/{${UserRepositoryArgs.username}}") {
        fun createRoute(username: String) = "user_repository/$username"
    }
}