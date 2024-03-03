package com.example.githubusernavigationdanapi.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubusernavigationdanapi.data.remote.response.DetailUserResponse
import com.example.githubusernavigationdanapi.di.Injection
import com.example.githubusernavigationdanapi.ui.ViewModelFactory
import com.example.githubusernavigationdanapi.ui.common.UiState
import com.example.githubusernavigationdanapi.ui.component.ProfileBar
import com.example.githubusernavigationdanapi.ui.component.SplashScreen
import com.example.githubusernavigationdanapi.ui.component.ViewPager
import com.example.githubusernavigationdanapi.ui.navigation.Screen
import com.example.githubusernavigationdanapi.ui.screen.favorite.FavoriteViewModel
import com.example.githubusernavigationdanapi.ui.screen.followers.FollowersViewModel
import com.example.githubusernavigationdanapi.ui.screen.followers.ListFollowersScreen
import com.example.githubusernavigationdanapi.ui.screen.followings.FollowingViewModel
import com.example.githubusernavigationdanapi.ui.screen.followings.ListFollowingsScreen
import com.example.githubusernavigationdanapi.ui.theme.GithubusernavigationdanapiTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    username: String,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection, LocalContext.current)
    ),
    viewModelFollowing: FollowingViewModel = viewModel(
        factory = ViewModelFactory(Injection, LocalContext.current)
    ),
    viewModelFollowers: FollowersViewModel = viewModel(
        factory = ViewModelFactory(Injection, LocalContext.current)
    ),
    viewModelFavorite: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection, LocalContext.current)
    ),
    navController: NavHostController = rememberNavController(),
){
    LaunchedEffect(true) {
        viewModel.getDetailUser(username)
        viewModelFollowing.getFollowings(username)
        viewModelFollowers.getFollowers(username)
        viewModelFavorite.getFavoriteUserByUsername(username)
    }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                SplashScreen()
            }

            is UiState.Success -> {
                DetailContent(
                    user = uiState.data,
                    navController = navController,
                    viewModelFollowing = viewModelFollowing,
                    viewModelFollowers = viewModelFollowers,
                    viewModelFavorite = viewModelFavorite
                )
            }

            is UiState.Error -> {
                Column {
                    Text(text = "Something Wrong!")
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    user:DetailUserResponse,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModelFollowing:FollowingViewModel,
    viewModelFollowers:FollowersViewModel,
    viewModelFavorite:FavoriteViewModel,
){
    val scope = rememberCoroutineScope()
    var job: Job? by remember { mutableStateOf(null) }
    var isFavorite by remember { mutableStateOf(false) }
    val userDetail by remember {
        mutableStateOf(
            DetailItem(
                photoUrl = user.avatarUrl,
                username = user.login,
                name = user.name,
                followers = user.followers,
                followings = user.following,
                work = user.company,
                location = user.location,
                id = user.id
            )
        )
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        ProfileBar(
            user = userDetail,
            modifier = modifier
                .padding(bottom = 20.dp)
        )

        Scaffold (
            topBar = {
                ViewPager(
                    navController = navController
                )
            }
        ) {
            innerPadding ->
            Box(
                modifier = modifier.padding(innerPadding)
            ) {
                NavHost(
                    navController =  navController,
                    startDestination = Screen.ListFollowings.route,
                    modifier = modifier
                        .fillMaxSize()
                ){
                    composable(
                        Screen.ListFollowers.route
                    ) {
                        ListFollowersScreen(viewModel = viewModelFollowers)
                    }

                    composable(
                        Screen.ListFollowings.route
                    ) {
                        ListFollowingsScreen(viewModel = viewModelFollowing)
                    }
                }

                viewModelFavorite.isFavorite.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when(uiState) {
                        is UiState.Success -> {
                            isFavorite = uiState.data
                            if(!isFavorite) {
                                FloatingActionButton(
                                    modifier = modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(horizontal = 20.dp, vertical = 20.dp),
                                    onClick = {
                                        job?.cancel()
                                        job = scope.launch {
                                            viewModelFavorite.insertUser(userDetail)
                                            isFavorite = true
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Filled.FavoriteBorder, contentDescription = null
                                    )
                                }
                            }
                            else {
                                FloatingActionButton(
                                    modifier = modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(horizontal = 20.dp, vertical = 20.dp),
                                    onClick = {
                                        job?.cancel()
                                        job = scope.launch {
                                            userDetail.id?.let { viewModelFavorite.deleteUserById(it) }
                                            isFavorite = false
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Filled.Favorite, contentDescription = null
                                    )
                                }
                            }
                        }

                        is UiState.Error -> {
                            Column {
                                Text(text = "Something Wrong!")
                            }
                        }

                        else -> {}
                    }
                }


            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun DetailContentPreview() {
    val navController:NavHostController = rememberNavController()
    val viewModelFollowing: FollowingViewModel = viewModel(
    factory = ViewModelFactory(Injection, LocalContext.current)
    )
    val viewModelFollowers: FollowersViewModel = viewModel(
        factory = ViewModelFactory(Injection, LocalContext.current)
    )
    val viewModelFavorite: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection, LocalContext.current)
    )
    GithubusernavigationdanapiTheme {
        DetailContent(user = DetailUserResponse(
            login = "Saputra",
            name = "Saputraaa"
        ),
            navController = navController,
            viewModelFollowing = viewModelFollowing,
            viewModelFollowers = viewModelFollowers,
            viewModelFavorite = viewModelFavorite
        )
    }
}