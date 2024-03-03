package com.example.githubusernavigationdanapi.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusernavigationdanapi.data.remote.response.DetailUserResponse
import com.example.githubusernavigationdanapi.data.repository.DetailUserRepository
import com.example.githubusernavigationdanapi.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: DetailUserRepository
): ViewModel() {
    private val _uiState:MutableStateFlow<UiState<DetailUserResponse>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DetailUserResponse>>
        get() = _uiState

    fun getDetailUser(username:String) {
        viewModelScope.launch{
            repository.detailUser(username)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{
                    detailUserResponse ->
                    _uiState.value = UiState.Success(detailUserResponse)
                }
        }
    }
}