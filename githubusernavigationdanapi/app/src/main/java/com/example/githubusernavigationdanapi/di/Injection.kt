package com.example.githubusernavigationdanapi.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.githubusernavigationdanapi.data.local.FavUserDatabase
import com.example.githubusernavigationdanapi.data.prefrences.SettingPreferences
import com.example.githubusernavigationdanapi.data.remote.retrofit.ApiService
import com.example.githubusernavigationdanapi.data.repository.DetailUserRepository
import com.example.githubusernavigationdanapi.data.repository.FavUserRepository
import com.example.githubusernavigationdanapi.data.repository.FollowersUserRepository
import com.example.githubusernavigationdanapi.data.repository.FollowingsUserRepository
import com.example.githubusernavigationdanapi.data.repository.HomeRepository
import com.example.githubusernavigationdanapi.data.repository.MyProfileRepository

object Injection {
    fun FavoriteRepository(context: Context): FavUserRepository {
        val database = FavUserDatabase.getInstance(context)
        val dao = database.favoriteDao()
        return FavUserRepository.getInstance(dao)
    }

    fun DetailRepository(apiService: ApiService): DetailUserRepository {
        return DetailUserRepository.getInstance(apiService)
    }

    fun HomeRepository(apiService: ApiService): HomeRepository {
        return HomeRepository.getInstance(apiService)
    }

    fun FollowingsRepository(apiService: ApiService): FollowingsUserRepository {
        return FollowingsUserRepository.getInstance(apiService)
    }

    fun FollowersRepository(apiService: ApiService): FollowersUserRepository {
        return FollowersUserRepository.getInstance(apiService)
    }

    fun SettingRepository(dataStore: DataStore<Preferences>): SettingPreferences {
        return SettingPreferences.getInstance(dataStore)
    }

    fun ProfileRepository(): MyProfileRepository {
        return MyProfileRepository.getInstance()
    }
}