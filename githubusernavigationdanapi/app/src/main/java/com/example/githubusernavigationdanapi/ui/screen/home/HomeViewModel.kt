package com.example.githubusernavigationdanapi.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusernavigationdanapi.data.remote.response.SearchUserResponse
import com.example.githubusernavigationdanapi.data.repository.HomeRepository
import com.example.githubusernavigationdanapi.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repository: HomeRepository
): ViewModel() {

    // state flow
    private val _uiState: MutableStateFlow<UiState<SearchUserResponse>> = MutableStateFlow(
        UiState.Loading)
    val uiState: StateFlow<UiState<SearchUserResponse>>
        get() = _uiState


    // state compose
    val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun setQuery(username:String) {
        _query.value = username
    }

    fun searchUsers(username:String) {
        if(username != "") {
            _query.value = username
            viewModelScope.launch{
                repository.searchUser(_query.value)
                    .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                    }
                    .collect{
                            searchUsers ->
                        _uiState.value = UiState.Success(searchUsers)
                    }
            }
        }
    }
}