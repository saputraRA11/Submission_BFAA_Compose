package com.example.githubusernavigationdanapi.data.repository

import com.example.githubusernavigationdanapi.data.remote.response.DetailUserResponse
import com.example.githubusernavigationdanapi.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DetailUserRepository private constructor(
    private val apiService: ApiService
){
    suspend fun detailUser(username:String): Flow<DetailUserResponse> {
        return flowOf(apiService.detailUser(username))
    }

    companion object {
        @Volatile
        private var instance: DetailUserRepository? = null
        fun getInstance(
            apiService: ApiService
        ): DetailUserRepository =
            instance ?: synchronized(this) {
                instance ?: DetailUserRepository(apiService)
            }.also { instance = it }
    }

}
