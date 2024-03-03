package com.example.githubusernavigationdanapi.data.repository

import com.example.githubusernavigationdanapi.data.model.DummyDataSource
import com.example.githubusernavigationdanapi.data.model.MyProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MyProfileRepository {
    fun getMyProfile(): Flow<MyProfile> {
        return flowOf(DummyDataSource.dummyProfile)
    }

    companion object {
        @Volatile
        private var instance: MyProfileRepository? = null

        fun getInstance(): MyProfileRepository =
            instance ?: synchronized(this) {
                MyProfileRepository().apply {
                    instance = this
                }
            }
    }
}