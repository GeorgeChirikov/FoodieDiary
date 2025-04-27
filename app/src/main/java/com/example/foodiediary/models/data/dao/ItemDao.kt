package com.example.foodiediary.models.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.foodiediary.models.data.entity.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE ean = :ean LIMIT 1")
    suspend fun getItemByEan(ean: Long): Item?

    @Insert
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)
}