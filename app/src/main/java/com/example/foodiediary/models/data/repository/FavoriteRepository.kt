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
        if (getFavoriteByEan(favorite.ean) == null) {
            favoriteDao.insert(favorite)
        } else {
            favoriteDao.update(favorite)
        }
    }

    suspend fun update(favorite: Favorite) {
        favoriteDao.update(favorite)
    }

    suspend fun delete(favorite: Favorite) {
        favoriteDao.delete(favorite)
    }
}