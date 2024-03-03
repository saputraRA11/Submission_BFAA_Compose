package com.example.githubusernavigationdanapi.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubusernavigationdanapi.data.remote.response.SearchUserResponse
import com.example.githubusernavigationdanapi.di.Injection
import com.example.githubusernavigationdanapi.ui.ViewModelFactory
import com.example.githubusernavigationdanapi.ui.common.UiState
import com.example.githubusernavigationdanapi.ui.component.Search
import com.example.githubusernavigationdanapi.ui.component.SplashScreen
import com.example.githubusernavigationdanapi.ui.component.UsersCard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection, LocalContext.current)
    ),
    navigateToDetail: (String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToFavorite: () -> Unit,
    navigateToAboutme: () -> Unit
) {
    val query by viewModel.query

    LaunchedEffect(true) {
        viewModel.searchUsers("a")
        viewModel.setQuery("")
    }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                SplashScreen()
            }

            is UiState.Success -> {
                Scaffold {
                    innerPadding ->
                    Column (
                        modifier = modifier
                            .padding(innerPadding)
                    ){
                        Search(
                            query = query,
                            onSearch = viewModel::searchUsers,
                            onQuery = viewModel::setQuery,
                            navigateToFavorite = navigateToFavorite,
                            navigateToSettings = navigateToSettings,
                            navigateToAboutme = navigateToAboutme
                        )
                        HomeContent(
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
fun HomeContent(
    users: SearchUserResponse,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    if(users.items?.isNotEmpty() == true) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            items(users.items){
                data ->
                if(data?.login != null && data.avatarUrl != null) {
                    UsersCard(
                        name =  data.login,
                        photoUrl =  data.avatarUrl,
                        modifier = modifier.clickable {
                            navigateToDetail(data.login)
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

