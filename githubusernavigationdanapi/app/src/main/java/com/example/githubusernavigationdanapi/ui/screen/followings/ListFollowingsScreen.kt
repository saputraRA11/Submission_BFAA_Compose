package com.example.githubusernavigationdanapi.ui.screen.followings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.githubusernavigationdanapi.data.remote.response.ListFollowingResponseItem
import com.example.githubusernavigationdanapi.ui.common.UiState
import com.example.githubusernavigationdanapi.ui.component.SplashScreen
import com.example.githubusernavigationdanapi.ui.component.UsersCard

@Composable
fun ListFollowingsScreen(
    modifier: Modifier = Modifier,
    viewModel: FollowingViewModel
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                SplashScreen()
            }

            is UiState.Success -> {
                FollowingsContent(
                    users = uiState.data
                )
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
@Composable
fun FollowingsContent(
    users: List<ListFollowingResponseItem>,
    modifier: Modifier = Modifier,
) {
    if(users.isNotEmpty() == true) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            items(users){
                    data ->
                if(data?.login != null && data.avatarUrl != null) {
                    UsersCard(
                        name =  data.login,
                        photoUrl =  data.avatarUrl
                    )
                }
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            Text(text = "")
        }
    }
}