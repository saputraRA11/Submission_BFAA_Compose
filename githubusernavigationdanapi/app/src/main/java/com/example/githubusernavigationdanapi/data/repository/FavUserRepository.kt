package com.example.githubusernavigationdanapi.data.repository

import com.example.githubusernavigationdanapi.data.local.FavoriteDao
import com.example.githubusernavigationdanapi.data.local.FavoriteUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FavUserRepository private constructor
    (
        private val favoriteDao: FavoriteDao
    )
{
    fun getFavUsers(): Flow<List<FavoriteUser>> = favoriteDao.getFavUsers()

    fun detailFavUsers(username: String): Flow<FavoriteUser> = favoriteDao.detailFavUsers(username)

    suspend fun insertUser(user: FavoriteUser):Flow<Boolean>{
        favoriteDao.insert(user).runCatching {
            return flowOf(false)
        }

        return flowOf(true)
    }

    suspend fun deleteUser(id: Int): Flow<Boolean>{
        favoriteDao.delete(id).runCatching {
            return flowOf(false)
        }

        return flowOf(true)
    }

    companion object {
        @Volatile
        private var instance: FavUserRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao
        ): FavUserRepository = instance ?: synchronized(this) {
            instance ?: FavUserRepository(favoriteDao)
        }.also { instance = it }
    }

}