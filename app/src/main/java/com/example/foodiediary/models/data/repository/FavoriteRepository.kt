package com.example.foodiediary.models.data.repository

import androidx.room.Query
import com.example.foodiediary.models.data.dao.FavoriteDao
import com.example.foodiediary.models.data.entity.Favorite

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val getAllFavorites = favoriteDao.getAllFavorites()

    suspend fun getFavoriteByEan(ean: Long): Favorite? {
        return favoriteDao.getFavoriteByEan(ean)
    }

    suspend fun insert(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    suspend fun delete(favorite: Favorite) {
        favoriteDao.delete(favorite)
    }
}