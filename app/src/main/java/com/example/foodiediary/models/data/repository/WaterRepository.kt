package com.example.foodiediary.models.data.repository

import com.example.foodiediary.models.data.dao.WaterDao
import com.example.foodiediary.models.data.entity.Water

class WaterRepository(private val waterDao: WaterDao) {

    fun getAllWater() = waterDao.getAllWater()

    // Get water item by time stamp
    suspend fun getWaterByTimeStamp(timeStamp: Long) = waterDao.getWaterByTimeStamp(timeStamp)

    // Get all water items sorted by time stamp
    fun getWaterInTimeStampRange (startTime: Long, endTime: Long) = waterDao.getWaterInTimeStampRange(startTime, endTime)

    // Insert a new water item
    suspend fun insert(water: Water) = waterDao.insert(water)

    // Delete a water item
    suspend fun delete(water: Water) = waterDao.delete(water)
}