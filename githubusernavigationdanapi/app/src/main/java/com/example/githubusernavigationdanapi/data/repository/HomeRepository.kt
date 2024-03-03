package com.example.githubusernavigationdanapi.data.repository

import com.example.githubusernavigationdanapi.data.remote.response.SearchUserResponse
import com.example.githubusernavigationdanapi.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HomeRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun searchUser(username:String): Flow<SearchUserResponse> {
        return flowOf(apiService.searchUser(username))
    }
    companion object {
        @Volatile
        private var instance: HomeRepository? = null
        fun getInstance(
            apiService: ApiService
        ): HomeRepository =
            instance ?: synchronized(this) {
                instance ?: HomeRepository(apiService)
            }.also { instance = it }
    }
}