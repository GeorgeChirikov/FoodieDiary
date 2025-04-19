package com.example.foodiediary.models.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long? = null,
    val username: String,
    val email: String,
    val hashedPassword: String,
    val age : Int,
    val weight : Double,
    val height : Double,
    val sex : String,
    // Ei toimi
    //val createdAt: Long = System.currentTimeMillis()
)
