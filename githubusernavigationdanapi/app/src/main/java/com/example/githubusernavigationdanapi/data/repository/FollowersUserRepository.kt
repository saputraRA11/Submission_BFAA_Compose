package com.example.githubusernavigationdanapi.data.repository

import com.example.githubusernavigationdanapi.data.remote.response.ListFollowerResponseItem
import com.example.githubusernavigationdanapi.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FollowersUserRepository private constructor(
    private val apiService: ApiService
){
    suspend fun listFollowers(username:String): Flow<List<ListFollowerResponseItem>> {
        return flowOf(apiService.followersUser(username))
    }

    companion object {
        @Volatile
        private var instance: FollowersUserRepository? = null
        fun getInstance(
            apiService: ApiService
        ): FollowersUserRepository =
            instance ?: synchronized(this) {
                instance ?: FollowersUserRepository(apiService)
            }.also { instance = it }
    }
}