package com.example.foodiediary.models.data.repository

import com.example.foodiediary.models.data.dao.ItemDao
import com.example.foodiediary.models.data.entity.Item

class ItemRepository(private val itemDao: ItemDao) {

    fun getAllItems() = itemDao.getAllItems()

    suspend fun getItemByEan(ean: Long): Item? {
        return itemDao.getItemByEan(ean)
    }

    suspend fun insert(item: Item) {
        if (getItemByEan(item.ean) == null) {
            itemDao.insert(item)
        } else {
            itemDao.update(item)
        }
    }

    suspend fun update(item: Item) {
        itemDao.update(item)
    }

    suspend fun delete(item: Item) {
        itemDao.delete(item)
    }
}