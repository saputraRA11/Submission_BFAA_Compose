package com.example.githubusernavigationdanapi.ui.screen.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusernavigationdanapi.data.local.FavoriteUser
import com.example.githubusernavigationdanapi.data.repository.FavUserRepository
import com.example.githubusernavigationdanapi.ui.common.UiState
import com.example.githubusernavigationdanapi.ui.screen.detail.DetailItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FavUserRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteUser>>> =
        MutableStateFlow(UiState.Loading)

    val uiState:StateFlow<UiState<List<FavoriteUser>>>
        get() = _uiState

    private val _isFavorite: MutableStateFlow<UiState<Boolean>> =
        MutableStateFlow(UiState.Loading)

    val isFavorite:StateFlow<UiState<Boolean>>
        get() = _isFavorite


    fun getFavorites() {
        viewModelScope.launch{
            repository.getFavUsers()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{
                        listFavorite ->
                    _uiState.value = UiState.Success(listFavorite)
                }
        }
    }

    fun getFavoriteUserByUsername(username:String) {
        viewModelScope.launch {
            repository.detailFavUsers(username)
                .collect{
                    user ->
                    if(user != null) {
                        _isFavorite.value = UiState.Success(true)
                    } else {
                        _isFavorite.value = UiState.Success(false)
                    }
                }.runCatching {
                    Log.d("Error db","gagal baca")
                    _isFavorite.value = UiState.Success(false)
                }
        }
    }

    fun insertUser(user:DetailItem) {
        viewModelScope.launch {
            if(user.id != null) {
                val newUser = FavoriteUser(
                    id = user.id,
                    username = user.username,
                    avatarUrl = user.photoUrl,
                )
                repository.insertUser(newUser)
                    .collect{
                            isInserted ->
                        _isFavorite.value = UiState.Success(isInserted)
                    }
            } else {
                _isFavorite.value = UiState.Success(false)
            }
        }
    }

    fun deleteUserById(id:Int) {
        viewModelScope.launch {
            repository.deleteUser(id)
                .collect{
                        isDelete ->
                    _isFavorite.value = UiState.Success(isDelete)
                }
        }
    }
}