package com.example.githubusernavigationdanapi.ui.screen.followings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusernavigationdanapi.data.remote.response.ListFollowingResponseItem
import com.example.githubusernavigationdanapi.data.repository.FollowingsUserRepository
import com.example.githubusernavigationdanapi.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FollowingViewModel(
    private val repository: FollowingsUserRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ListFollowingResponseItem>>> = MutableStateFlow(
        UiState.Loading)
    val uiState: StateFlow<UiState<List<ListFollowingResponseItem>>>
        get() = _uiState

    fun getFollowings(username:String) {
        viewModelScope.launch{
            repository.listFollowings(username)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                    Log.d("Error Api",it.message.toString())
                }
                .collect{
                        listFollowingItems ->
                    _uiState.value = UiState.Success(listFollowingItems)
                }
        }
    }
}