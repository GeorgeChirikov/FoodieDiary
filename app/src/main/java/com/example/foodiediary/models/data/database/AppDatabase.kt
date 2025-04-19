package com.example.foodiediary.models.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodiediary.models.Converters
import com.example.foodiediary.models.data.dao.ItemDao
import com.example.foodiediary.models.data.dao.UserDao
import com.example.foodiediary.models.data.entity.Item
import com.example.foodiediary.models.data.entity.User

@Database(entities = [User::class, Item::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun itemDao(): ItemDao


    // Pitää selvittää mitä tekee
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "foodie_diary_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
