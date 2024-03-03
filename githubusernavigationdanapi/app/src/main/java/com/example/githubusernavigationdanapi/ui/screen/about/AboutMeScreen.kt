package com.example.githubusernavigationdanapi.ui.screen.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubusernavigationdanapi.data.model.MyProfile
import com.example.githubusernavigationdanapi.di.Injection
import com.example.githubusernavigationdanapi.ui.ViewModelFactory
import com.example.githubusernavigationdanapi.ui.common.UiState
import com.example.githubusernavigationdanapi.ui.component.AboutBar
import com.example.githubusernavigationdanapi.ui.component.SplashScreen

@Composable
fun AboutMewScreen(
    modifier: Modifier = Modifier,
    viewModel: AboutViewModel = viewModel(
        factory = ViewModelFactory(Injection, LocalContext.current)
    )
) {
    LaunchedEffect(true) {
        viewModel.getProfile()
    }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        uiState ->
        when(uiState) {
            is UiState.Loading -> {
                SplashScreen()
            }
            is UiState.Success -> {
                AboutmMeContent(
                    profile = uiState.data
                )
            }
            else ->{}
        }
    }
}

@Composable
fun AboutmMeContent(
    profile:MyProfile,
    modifier:Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ){
        AboutBar(profile = profile)
    }
}