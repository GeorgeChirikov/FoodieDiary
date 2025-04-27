package com.example.foodiediary.models.data.repository

import com.example.foodiediary.models.data.dao.AddedDao
import com.example.foodiediary.models.data.entity.Added

class AddedRepository(private val addedDao: AddedDao) {
    // Get all added items
    val allAdded = addedDao.getAllAdded()

    // Get added item by EAN
    suspend fun getAddedByEan(ean: Long) = addedDao.getAddedByEan(ean)

    // Get items sorted by day
    val itemsSortedByTimeStamp = addedDao.getItemsSortedByTimeStamp()

    // Get items by EAN sorted by
    fun getItemsByEanSortedByTimeStamp(ean: Long) = addedDao.getItemsByEanSortedByTimeStamp(ean)

    // Get added item by time stamp
    suspend fun getAddedByTimeStamp(timeStamp: Long) = addedDao.getAddedByTimeStamp(timeStamp)

    // Get items in timestamp range
    fun getItemsInTimeStampRange(startTime: Long, endTime: Long) =
        addedDao.getItemsInTimeStampRange(startTime, endTime)

    suspend fun insert(added: Added) = addedDao.insert(added)

    suspend fun delete(added: Added) = addedDao.delete(added)
}