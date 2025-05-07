package com.example.foodiediary.utils

import android.content.Context
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.entity.Added
import com.example.foodiediary.models.data.entity.Favorite
import com.example.foodiediary.models.data.entity.Item
import com.example.foodiediary.models.data.repository.AddedRepository
import com.example.foodiediary.models.data.repository.FavoriteRepository
import com.example.foodiediary.models.data.repository.ItemRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


// CLEANUP: This class AND IN MAIN ACTIVITY

// This class is only for ui mocking
class DataInjectionUtilClass(context: Context) {

    val itemDao = AppDatabase.getInstance(context).itemDao()
    val itemRepository = ItemRepository(itemDao)
    val addedDao = AppDatabase.getInstance(context).addedDao()
    val addedRepository = AddedRepository(addedDao)
    val favoriteDao = AppDatabase.getInstance(context).favoriteDao()
    val favoriteRepository = FavoriteRepository(favoriteDao)

    suspend fun injectData() {
        // Mock data for items
        val items = listOf(
            Item(1234567890123, "Apple", 52.0, 0.2, 14.0, 10.0, 2.4, 0.3, 0.01),
            Item(2345678901234, "Banana", 89.0, 0.3, 23.0, 12.0, 2.6, 1.1, 0.01),
            Item(3456789012345, "Orange", 47.0, 0.1, 12.0, 9.0, 2.4, 0.9, 0.01),
            Item(4567890123456, "Grapes", 69.0, 0.2, 18.0, 16.0, 0.9, 0.6, 0.01),
            Item(5678901234567, "Strawberry", 32.0, 0.3, 7.7, 4.9, 2.0, 0.7, 0.01),
            Item(6789012345678, "Watermelon", 30.0, 0.2, 8.0, 6.0, 0.4, 0.6, 0.01),
            Item(7890123456789, "Pineapple", 50.0, 0.1, 13.0, 10.0, 1.4, 0.5, 0.01),
            Item(8901234567890, "Mango", 60.0, 0.4, 15.0, 14.0, 1.6, 0.8, 0.01),
        )

        // Simulate saving items to the database
        coroutineScope {
            launch {
                items.forEach { item ->
                    // Save item to database
                    itemRepository.insert(item)
                    val added = Added(
                        ean = item.ean
                    )
                    //addedRepository.insert(added)
                    val favorite = Favorite(
                        ean = item.ean
                    )
                    favoriteRepository.insert(favorite)
                }
            }
        }
    }
}