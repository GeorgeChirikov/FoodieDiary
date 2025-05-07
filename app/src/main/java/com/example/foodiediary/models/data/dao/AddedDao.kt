package com.example.foodiediary.models.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.foodiediary.models.data.entity.Added
import kotlinx.coroutines.flow.Flow

@Dao
interface AddedDao {

    @Query("SELECT * FROM added_items")
    fun getAllAdded(): Flow<List<Added>>

    @Query("SELECT * FROM added_items WHERE ean = :ean LIMIT 1")
    suspend fun getAddedByEan(ean: Long): Added?

    @Query("SELECT * FROM added_items ORDER BY timestamp ASC")
    fun getItemsSortedByTimeStamp(): Flow<List<Added>>

    @Query("SELECT * FROM added_items where ean = :ean ORDER BY timestamp ASC")
    fun getItemsByEanSortedByTimeStamp(ean: Long): Flow<List<Added>>

    @Query("SELECT * FROM added_items WHERE timeStamp = :timeStamp")
    suspend fun getAddedByTimeStamp(timeStamp: Long): Added?

    @Query("SELECT * FROM added_items WHERE timeStamp BETWEEN :startTime AND :endTime")
    fun getItemsInTimeStampRange(startTime: Long, endTime: Long): Flow<List<Added>>

    @Insert
    suspend fun insert(added: Added)

    @Update
    suspend fun update(added: Added)

    @Delete
    suspend fun delete(added: Added)

    @Query("DELETE FROM added_items where ean = :ean")
    suspend fun deleteByEan(ean: Long)

    @Query("DELETE FROM added_items where timeStamp = :timeStamp")
    suspend fun deleteByTimeStamp(timeStamp: Long)
}