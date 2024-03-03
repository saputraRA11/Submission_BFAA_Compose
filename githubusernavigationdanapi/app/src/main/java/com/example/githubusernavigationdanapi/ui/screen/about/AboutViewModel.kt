package com.example.githubusernavigationdanapi.ui.screen.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusernavigationdanapi.data.model.MyProfile
import com.example.githubusernavigationdanapi.data.repository.MyProfileRepository
import com.example.githubusernavigationdanapi.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AboutViewModel(
    private val repository: MyProfileRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<MyProfile>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<MyProfile>>
        get() = _uiState

    fun getProfile() {
        viewModelScope.launch{
            repository.getMyProfile()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{
                    profile ->
                    _uiState.value = UiState.Success(profile)
                }
        }
    }
}