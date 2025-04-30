package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.repository.FavoriteRepository
import com.example.foodiediary.models.data.repository.ItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class HomeViewModel(context: Context) : ViewModel() {
    val itemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())
    val allItems = itemRepository.getAllItems()
    val favoriteRepository = FavoriteRepository(AppDatabase.getInstance(context).favoriteDao())
    val allFavorites = favoriteRepository.getAllFavorites

    @OptIn(ExperimentalCoroutinesApi::class)
    val allFavoriteItems = allFavorites.flatMapLatest { favorites ->
        allItems.map { items ->
            items.filter { item ->
                favorites.any { favorite -> favorite.ean == item.ean }
            }
        }
    }
}