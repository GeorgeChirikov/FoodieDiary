package com.example.foodiediary.models

import androidx.room.TypeConverter
import java.sql.Date

class Converters {
    // Pitää selvittää miten käytetään
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}