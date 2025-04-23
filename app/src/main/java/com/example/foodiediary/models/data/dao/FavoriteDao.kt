package com.example.foodiediary.models.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodiediary.models.data.entity.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}