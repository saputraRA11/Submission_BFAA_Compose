package com.example.githubusernavigationdanapi.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubusernavigationdanapi.data.local.FavoriteUser
import com.example.githubusernavigationdanapi.di.Injection
import com.example.githubusernavigationdanapi.ui.ViewModelFactory
import com.example.githubusernavigationdanapi.ui.common.UiState
import com.example.githubusernavigationdanapi.ui.component.SplashScreen
import com.example.githubusernavigationdanapi.ui.component.UsersCard

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection, LocalContext.current)
    ),
    navigateToDetail: (String) -> Unit,
) {
    LaunchedEffect(true) {
        viewModel.getFavorites()
    }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                SplashScreen()
            }

            is UiState.Success -> {
                Scaffold (
                    topBar = {
                        Row (
                            modifier = modifier
                                .padding(25.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            )

                            Text(text = "Favorite Menu")
                        }
                    }
                ){
                    innerPadding ->
                    Column (
                        modifier = modifier
                            .padding(innerPadding)
                    ){
                        FavoriteContent(
                            users = uiState.data,
                            modifier = modifier,
                            navigateToDetail = navigateToDetail
                        )
                    }
                }
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
fun FavoriteContent(
    users: List<FavoriteUser>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    if(users.isNotEmpty() == true) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            items(users){
                data ->
                if(data?.username != null && data.avatarUrl != null) {
                    UsersCard(
                        name = data.username!!,
                        photoUrl = data.avatarUrl!!,
                        modifier = modifier.clickable {
                            navigateToDetail(data.username!!)
                        }
                    )
                }
            }
        }
    } else {
        Column {
            Text(text = "check your connection!")
        }
    }
}