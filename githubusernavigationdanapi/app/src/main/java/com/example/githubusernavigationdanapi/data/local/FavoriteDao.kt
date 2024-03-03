package com.example.githubusernavigationdanapi.data.local

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * from FavoriteUser WHERE username = :username")
    fun detailFavUsers(username: String): Flow<FavoriteUser>

    @Query("SELECT * from FavoriteUser ORDER BY username ASC")
    fun getFavUsers(): Flow<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(SQLiteException::class)
    suspend fun insert(user: FavoriteUser)

    @Query("DELETE FROM FavoriteUser WHERE id = :id")
    @Throws(SQLiteException::class)
    suspend fun delete(id: Int)
}