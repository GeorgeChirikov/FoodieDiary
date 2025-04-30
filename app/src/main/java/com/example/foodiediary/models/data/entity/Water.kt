package com.example.foodiediary.models.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water")
data class Water(
    @PrimaryKey
    val timeStamp: Long = System.currentTimeMillis(),
    val amount: Double
)
