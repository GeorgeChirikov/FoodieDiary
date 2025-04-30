package com.example.foodiediary.models.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodiediary.models.data.entity.Water
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {
    @Query("SELECT * FROM water")
    fun getAllWater(): Flow<List<Water>>

    @Query("SELECT * FROM water WHERE timeStamp = :timeStamp")
    suspend fun getWaterByTimeStamp(timeStamp: Long): Water?

    @Query("SELECT * FROM water WHERE timeStamp BETWEEN :startTime AND :endTime")
    fun getWaterInTimeStampRange(startTime: Long, endTime: Long): Flow<List<Water>>

    @Insert
    suspend fun insert(water: Water)

    @Delete
    suspend fun delete(water: Water)
}