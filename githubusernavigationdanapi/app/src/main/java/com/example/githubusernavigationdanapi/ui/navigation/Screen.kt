package com.example.githubusernavigationdanapi.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")

    object Favorite : Screen("favorite")
    object Settings : Screen("settings")
    object DetailUser : Screen("home/{username}") {
        fun createRoute(username: String) = "home/$username"
    }

    object ListFollowings : Screen("list_followings")


    object ListFollowers : Screen("list_followers")


    object AboutMe : Screen("about_me")
}