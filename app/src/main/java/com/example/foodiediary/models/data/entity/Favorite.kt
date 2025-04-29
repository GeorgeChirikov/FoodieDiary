package com.example.foodiediary.models.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    /*
    foreignKeys = [
        ForeignKey(
            entity = Item::class,
            parentColumns = ["ean"],
            childColumns = ["itemEan"],
            onDelete = ForeignKey.CASCADE
        )
    ]

     */
)
data class Favorite (
    @PrimaryKey
    val ean : Long
)