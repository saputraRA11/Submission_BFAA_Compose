package com.example.githubusernavigationdanapi.data.repository

import android.util.Log
import com.example.githubusernavigationdanapi.data.remote.response.ListFollowingResponseItem
import com.example.githubusernavigationdanapi.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf

class FollowingsUserRepository private constructor(
    private val apiService: ApiService
){
    suspend fun listFollowings(username:String): Flow<List<ListFollowingResponseItem>> {
       return flowOf(apiService.followingUser(username)).catch {
           Log.d("Error Api",it.message.toString())
       }
    }

    companion object {
        @Volatile
        private var instance: FollowingsUserRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): FollowingsUserRepository =
            instance ?: synchronized(this) {
                instance ?: FollowingsUserRepository(apiService)
            }.also { instance = it }
    }
}