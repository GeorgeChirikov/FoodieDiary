package com.example.foodiediary.models.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite (
    @PrimaryKey
    val ean : Long,
    val name: String,
    val energy: Double,
    val fat: Double,
    val carbohydrates: Double,
    val sugar: Double,
    val fiber: Double,
    val protein: Double,
    val salt: Double,
    val review: Double
)