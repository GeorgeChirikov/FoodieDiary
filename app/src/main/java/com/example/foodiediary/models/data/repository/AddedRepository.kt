package com.example.foodiediary.models.data.repository

import com.example.foodiediary.models.data.dao.AddedDao
import com.example.foodiediary.models.data.entity.Added
import kotlinx.coroutines.flow.Flow

class AddedRepository(private val addedDao: AddedDao) {

    // Get added item by EAN
    suspend fun getAddedByEan(ean: Long) = addedDao.getAddedByEan(ean)

    // Get items sorted by day
    val itemsSortedByTimeStamp = addedDao.getItemsSortedByTimeStamp()

    // Get items by EAN sorted by
    fun getItemsByEanSortedByTimeStamp(ean: Long) = addedDao.getItemsByEanSortedByTimeStamp(ean)

    // Get added item by time stamp
    suspend fun getAddedByTimeStamp(timeStamp: Long) = addedDao.getAddedByTimeStamp(timeStamp)

    suspend fun insert(added: Added) {
        if (getAddedByEan(added.ean) == null) {
            addedDao.insert(added)
        } else {
            addedDao.update(added)
        }
    }

    suspend fun delete(added: Added) = addedDao.delete(added)

    fun getAllAdded(): Flow<List<Added>> {
        return addedDao.getAllAdded()
    }
}