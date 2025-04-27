package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.room.Room
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.entity.Added
import com.example.foodiediary.models.data.entity.Favorite
import com.example.foodiediary.models.data.entity.Item
import kotlinx.coroutines.flow.Flow

// pitääks implementoida ViewModel()
class DatabaseViewModel(context: Context) {
    private val db = AppDatabase.getInstance(context)

    val itemDao = db.itemDao()
    val items: Flow<List<Item>> = itemDao.getAllItems()
    val favoriteDao = db.favoriteDao()
    // väliaikaisesti itemDao.getAllItems() pitäs olla favoriteDao.getAllFavorites()
    val favorites: Flow<List<Item>> = itemDao.getAllItems()
    val addedDao = db.addedDao()

    suspend fun getItemByEan(ean: Long): Item? {
        return itemDao.getItemByEan(ean)
    }

    suspend fun insertItem(item: Item) {
        itemDao.insertItem(item)
    }

    suspend fun deleteItem(item: Item) {
        itemDao.deleteItem(item)
    }

    suspend fun insertAdded(added: Added) {
        addedDao.insertAdded(added)
    }

    suspend fun getAddedByTimeStamp(timeStamp: Long): Added? {
        return addedDao.getAddedByTimeStamp(timeStamp)
    }

    fun getItemsInTimestampRange(startTime: Long, endTime: Long): Flow<List<Added>> {
        return addedDao.getItemsInTimestampRange(startTime, endTime)
    }


}