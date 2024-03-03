package com.example.githubusernavigationdanapi.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusernavigationdanapi.data.prefrences.dataStore
import com.example.githubusernavigationdanapi.data.remote.retrofit.ApiConfig
import com.example.githubusernavigationdanapi.di.Injection
import com.example.githubusernavigationdanapi.ui.screen.about.AboutViewModel
import com.example.githubusernavigationdanapi.ui.screen.detail.DetailViewModel
import com.example.githubusernavigationdanapi.ui.screen.favorite.FavoriteViewModel
import com.example.githubusernavigationdanapi.ui.screen.followers.FollowersViewModel
import com.example.githubusernavigationdanapi.ui.screen.followings.FollowingViewModel
import com.example.githubusernavigationdanapi.ui.screen.home.HomeViewModel
import com.example.githubusernavigationdanapi.ui.screen.setting.SettingsViewModel

class ViewModelFactory(
    private val injection: Injection,
    private val context: Context,
    private val dataStore: DataStore<Preferences> = context.dataStore
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = ApiConfig.getApiService()

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(injection.HomeRepository(apiService)) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(injection.DetailRepository(apiService)) as T
        } else if (modelClass.isAssignableFrom(FollowersViewModel::class.java)) {
            return FollowersViewModel(injection.FollowersRepository(apiService)) as T
        } else if (modelClass.isAssignableFrom(FollowingViewModel::class.java)) {
            return FollowingViewModel(injection.FollowingsRepository(apiService)) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(injection.FavoriteRepository(context)) as T
        } else if(modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(injection.SettingRepository(dataStore)) as T
        } else if(modelClass.isAssignableFrom(AboutViewModel::class.java)) {
            return AboutViewModel(injection.ProfileRepository()) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}