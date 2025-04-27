package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.repository.FavoriteRepository
import com.example.foodiediary.models.data.repository.ItemRepository

class FavoritesViewModel(context: Context) : ViewModel() {
    val itemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())
    val favoritesRepository = FavoriteRepository(AppDatabase.getInstance(context).favoriteDao())
    val allFavorites = favoritesRepository.getAllFavorites
    val allItems = itemRepository.getAllItems()
}