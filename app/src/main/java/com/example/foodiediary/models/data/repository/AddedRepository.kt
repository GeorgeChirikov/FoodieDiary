package com.example.foodiediary.models.data.repository

import com.example.foodiediary.models.data.dao.AddedDao
import com.example.foodiediary.models.data.entity.Added
import kotlinx.coroutines.flow.Flow

class AddedRepository(private val addedDao: AddedDao) {

    // Get added item by EAN
    internal suspend fun getAddedByEan(ean: Long) = addedDao.getAddedByEan(ean)

    suspend fun insert(added: Added) {
        addedDao.insert(added)
    }

    suspend fun delete(added: Added) = addedDao.delete(added)

    suspend fun deleteByEan(ean: Long) = addedDao.deleteByEan(ean)

    suspend fun deleteByTimeStamp(timeStamp: Long) = addedDao.deleteByTimeStamp(timeStamp)

    fun getAllAdded(): Flow<List<Added>> {
        return addedDao.getAllAdded()
    }

    fun getItemsInTimeStampRange(startTime: Long, endTime: Long): Flow<List<Added>> {
        return addedDao.getItemsInTimeStampRange(startTime, endTime)
    }
}