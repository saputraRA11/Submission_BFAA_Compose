package com.example.githubusernavigationdanapi.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusernavigationdanapi.data.prefrences.SettingPreferences
import com.example.githubusernavigationdanapi.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SettingsViewModel (
    private val repository: SettingPreferences
): ViewModel() {

    // state flow
    private val _uiState: MutableStateFlow<UiState<Boolean>> = MutableStateFlow(
        UiState.Loading)
    val uiState: StateFlow<UiState<Boolean>>
        get() = _uiState


    fun getThemeSettings(){
        viewModelScope.launch {
            repository.getThemeSetting().catch {

            }.collect{
                result ->
                _uiState.value = UiState.Success(result)
            }
        }
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repository.saveThemeSetting(isDarkModeActive)
        }
    }
}