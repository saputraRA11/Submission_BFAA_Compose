package com.example.githubusernavigationdanapi.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.githubusernavigationdanapi.ui.navigation.NavigationItem
import com.example.githubusernavigationdanapi.ui.navigation.Screen
import com.example.githubusernavigationdanapi.ui.theme.GithubusernavigationdanapiTheme

@Composable()
fun ViewPager(
    navController: NavHostController
) {
    NavigationBar(
        modifier = Modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = "Followings",
                icon = Icons.Filled.Face,
                screen = Screen.ListFollowings.route
            ),
            NavigationItem(
                title = "Followers",
                icon = Icons.Filled.Favorite,
                screen = Screen.ListFollowers.route
            ),
        )

        navigationItems.map {
                item ->
            NavigationBarItem(
                icon={
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                    )
                },
                label = { Text(item.title)},
                selected = currentRoute == item.screen,
                onClick = {
                    navController.navigate(item.screen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

    }
}

@Preview(
    showBackground = true
)
@Composable
fun ViewPagerPreview() {
    val navController:NavHostController = rememberNavController()
    GithubusernavigationdanapiTheme {
        ViewPager(navController)
    }
}