package com.example.githubusernavigationdanapi.ui.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubusernavigationdanapi.di.Injection
import com.example.githubusernavigationdanapi.ui.ViewModelFactory
import com.example.githubusernavigationdanapi.ui.common.UiState
import com.example.githubusernavigationdanapi.ui.component.SplashScreen
import com.example.githubusernavigationdanapi.ui.component.SwithMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(
        factory = ViewModelFactory(Injection, LocalContext.current)
    )
) {
    LaunchedEffect(true) {
        viewModel.getThemeSettings()
    }

    var checked by remember { mutableStateOf(false) }
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        uiState ->
        when(uiState) {
            is UiState.Loading -> {
                SplashScreen()
            }
            is UiState.Success -> {
                checked = uiState.data
                SettingsContent(
                    checked = checked,
                    onCheckedChange = {
                        checked = !checked
                        viewModel.saveThemeSetting(checked)
                    }
                )
            }
            else ->{}
        }
    }
}

@Composable
fun SettingsContent(
    checked:Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier:Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var job: Job? by remember { mutableStateOf(null) }

    Row(
        modifier = modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Dark Mode",
            modifier = modifier
                .padding(horizontal = 20.dp)
        )

        SwithMode(
            checked = checked,
            onCheckedChange = {
                job?.cancel()
                job = scope.launch {
                    onCheckedChange(checked)
                }
            })
    }
}