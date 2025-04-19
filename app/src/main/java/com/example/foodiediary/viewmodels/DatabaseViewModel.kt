package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.room.Room
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.entity.Item
import kotlinx.coroutines.flow.Flow

// pitääks implementoida ViewModel()
class DatabaseViewModel(context: Context) {
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "foodie-diary-db"
    ).build()

    val itemDao = db.itemDao()
    val items: Flow<List<Item>> = itemDao.getAllItems()

    suspend fun getItemByEan(ean: Long): Item? {
        return itemDao.getItemByEan(ean)
    }

    suspend fun insertItem(item: Item) {
        itemDao.insertItem(item)
    }

    suspend fun deleteItem(item: Item) {
        itemDao.deleteItem(item)
    }


}