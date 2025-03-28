package com.example.foodiediary.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

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
    val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

)
