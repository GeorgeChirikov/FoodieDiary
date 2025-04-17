package com.example.foodiediary.models.data.entity

import androidx.room.Entity
import java.sql.Timestamp

@Entity(tableName = "list_of_favorites")
data class ListOfFavorites(
    val userId: Long,
    val ean: Long,
    val createdAt: Timestamp = Timestamp(System.currentTimeMillis())
)
