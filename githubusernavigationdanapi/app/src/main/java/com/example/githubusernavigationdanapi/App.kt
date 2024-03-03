package com.example.githubusernavigationdanapi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubusernavigationdanapi.ui.navigation.Screen
import com.example.githubusernavigationdanapi.ui.screen.about.AboutMewScreen
import com.example.githubusernavigationdanapi.ui.screen.detail.DetailScreen
import com.example.githubusernavigationdanapi.ui.screen.favorite.FavoriteScreen
import com.example.githubusernavigationdanapi.ui.screen.home.HomeScreen
import com.example.githubusernavigationdanapi.ui.screen.setting.SettingsScreen

@Composable
fun GithubApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    // router
    NavHost(
        navController =  navController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetail = {
                    username ->
                    navController.navigate(Screen.DetailUser.createRoute(username))
                },
                navigateToFavorite = {
                    navController.navigate(Screen.Favorite.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        restoreState = true
                        launchSingleTop = true
                    }
                },
                navigateToSettings = {
                    navController.navigate(Screen.Settings.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        restoreState = true
                        launchSingleTop = true
                    }
                },
                navigateToAboutme = {
                    navController.navigate(Screen.AboutMe.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }

        composable(Screen.AboutMe.route) {
            AboutMewScreen()
        }

        composable(Screen.Favorite.route) {
            FavoriteScreen(
                navigateToDetail = {
                        username ->
                    navController.navigate(Screen.DetailUser.createRoute(username))
                }
            )
        }

        composable(
            Screen.DetailUser.route,
            arguments = listOf(navArgument("username"){
                type = NavType.StringType
            })
        ) {
            val username = it.arguments?.getString("username") ?: ""

            DetailScreen(
                username = username,
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun GithubAppPreview() {
    HomeScreen(
        navigateToDetail = {},
        navigateToSettings = {},
        navigateToFavorite = {},
        navigateToAboutme = {}
    )
}