package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.repository.FavoriteRepository
import com.example.foodiediary.models.data.repository.ItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

/**
 * FavoritesViewModel is a ViewModel class that manages the data and operations for the favorites screen.
 * It interacts with the database to perform CRUD operations on items and favorites.
 *
 * @param context The context used to access the database.
 *
 * This ViewModel uses the ItemRepository and FavoriteRepository to interact with the database.
 * It exposes a Flow for all items and a Flow for all favorite items.
 *
 * This ViewModel is used in the Favorites screen to display the list of favorite items.
 *
 * @property allItems Flow<List<Item>>: A Flow that emits the list of all items from the database.
 * @property allFavoriteItems Flow<List<Item>>: A Flow that emits the list of favorite items from the database.
 * @property allFavorites Flow<List<Favorite>>: A Flow that emits the list of all favorites from the database.
 *
 */
class FavoritesViewModel(context: Context) : ViewModel() {

    val itemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())
    val favoritesRepository = FavoriteRepository(AppDatabase.getInstance(context).favoriteDao())
    val allFavorites = favoritesRepository.getAllFavorites
    val allItems = itemRepository.getAllItems()

    @OptIn(ExperimentalCoroutinesApi::class)
    val allFavoriteItems = allFavorites.flatMapLatest { favorites ->
        allItems.map { items ->
            items.filter { item ->
                favorites.any { favorite -> favorite.ean == item.ean }
            }
        }
    }
}