package com.example.foodiediary.models.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodiediary.models.data.entity.Added

@Dao
interface AddedDao {

    @Query("SELECT * FROM added_items")
    suspend fun getAllAdded(): List<Added>

    @Query("SELECT * FROM added_items WHERE ean = :ean LIMIT 1")
    suspend fun getAddedByEan(ean: Long): Added?

    @Query("SELECT * FROM added_items ORDER BY timestamp ASC")
    fun getItemsSortedByDay(): List<Added>

    @Insert
    suspend fun insertAdded(added: Added)

    @Delete
    suspend fun deleteAdded(added: Added)
}