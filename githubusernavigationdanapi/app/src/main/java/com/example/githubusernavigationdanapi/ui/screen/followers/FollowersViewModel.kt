package com.example.githubusernavigationdanapi.ui.screen.followers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusernavigationdanapi.data.remote.response.ListFollowerResponseItem
import com.example.githubusernavigationdanapi.data.repository.FollowersUserRepository
import com.example.githubusernavigationdanapi.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FollowersViewModel(
    private val repository: FollowersUserRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ListFollowerResponseItem>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<ListFollowerResponseItem>>>
        get() = _uiState

    fun getFollowers(username:String) {
        viewModelScope.launch{
            repository.listFollowers(username)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{
                        listFollowerItems ->
                    _uiState.value = UiState.Success(listFollowerItems)
                }
        }
    }
}