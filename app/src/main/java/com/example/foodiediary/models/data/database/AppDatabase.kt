package com.example.foodiediary.models.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.foodiediary.models.Converters
import com.example.foodiediary.models.data.dao.FavoriteDao
import com.example.foodiediary.models.data.dao.ItemDao
import com.example.foodiediary.models.data.entity.Favorite
import com.example.foodiediary.models.data.entity.Item

@Database(
    entities = [Item::class, Favorite::class],
    version = 2,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun favoriteDao(): FavoriteDao



    // Pitää selvittää mitä tekee
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Drop the old table users
                database.execSQL("DROP TABLE IF EXISTS `users`")

                // Create the new table for favorites
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `favorites` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `ean` INTEGER NOT NULL, 
                        `name` TEXT NOT NULL, 
                        `protein` REAL NOT NULL, 
                        `fat` REAL NOT NULL, 
                        `carbohydrates` REAL NOT NULL, 
                        `energy` REAL NOT NULL
                    )
                """.trimIndent())
            }
        }

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "foodie_diary_database"
                    )
                        .addMigrations(MIGRATION_1_2)
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
