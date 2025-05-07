package com.example.foodiediary.models.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "added_items")
data class Added (
    @PrimaryKey
    val timeStamp: Long = System.currentTimeMillis(),
    val ean: Long
)
