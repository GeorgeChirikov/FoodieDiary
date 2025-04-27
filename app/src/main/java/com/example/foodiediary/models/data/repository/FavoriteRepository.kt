package com.example.foodiediary.models.data.repository

import com.example.foodiediary.models.data.dao.FavoriteDao
import com.example.foodiediary.models.data.entity.Favorite

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val getAllFavorites = favoriteDao.getAllFavorites()

    suspend fun insert(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    suspend fun delete(favorite: Favorite) {
        favoriteDao.delete(favorite)
    }
}