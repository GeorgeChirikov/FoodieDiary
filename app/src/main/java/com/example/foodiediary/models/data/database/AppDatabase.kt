package com.example.foodiediary.models.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.foodiediary.models.Converters
import com.example.foodiediary.models.data.dao.AddedDao
import com.example.foodiediary.models.data.dao.FavoriteDao
import com.example.foodiediary.models.data.dao.ItemDao
import com.example.foodiediary.models.data.entity.Added
import com.example.foodiediary.models.data.entity.Favorite
import com.example.foodiediary.models.data.entity.Item

@Database(
    entities = [Item::class, Favorite::class, Added::class],
    version = 5,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun addedDao(): AddedDao



    // Pitää selvittää mitä tekee
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /*
            MIGRAATIOITA EI SAA POISTAA.
            Se on tärkeä ja pysyvä, koska...
            se varmistaa, että käyttäjän tiedot eivät katoa kun tietokanta päivitetään versiosta 1 versioon 2
            ja kun päivitetään versiosta 2 versioon 3 (eli MIGRATION_2_3) jos käyttäjällä on vieläkin versio 1 niin
            MIGRATION_1_2 auttaa käyttäjää siirtämään tiedot versiosta 1 versioon 2 ja sitten
            MIGRATION_2_3 auttaa käyttäjää siirtämään tiedot versiosta 2 versioon 3
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Drop the old table users
                database.execSQL("DROP TABLE IF EXISTS `users`")

                // Create the new table for favorites
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `favorites` (
                        `id` INTEGER PRIMARY KEY NOT NULL, 
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

        // EI SAA POISTAA
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {

                // Step 1: Create a new table without the `review` column
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `items_new` (
                        `ean` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `name` TEXT NOT NULL, 
                        `energy` REAL NOT NULL, 
                        `fat` REAL NOT NULL, 
                        `carbohydrates` REAL NOT NULL, 
                        `sugar` REAL NOT NULL,
                        `fiber` REAL NOT NULL,
                        `protein` REAL NOT NULL,
                        `salt` REAL NOT NULL
                    )
                """.trimIndent())

                // Step 2: Copy data from the old table to the new table
                database.execSQL("""
                    INSERT INTO `items_new` (`ean`, `name`, `energy`, `fat`, `carbohydrates`, `sugar`, `fiber`, `protein`, `salt`)
                    SELECT `ean`, `name`, `energy`, `fat`, `carbohydrates`, `sugar`, `fiber`, `protein`, `salt`
                    FROM `items`
                """.trimIndent())

                // Step 3: Drop the old table
                database.execSQL("DROP TABLE IF EXISTS `items`")

                // Step 4: Rename the new table to the old table's name
                database.execSQL("ALTER TABLE `items_new` RENAME TO `items`")

            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {


                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `added_items` (
                        `timeStamp` INTEGER PRIMARY KEY NOT NULL,
                        `ean` INTEGER NOT NULL
                    )
                """.trimIndent())

                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `favorites_new` (
                        `ean` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `name` TEXT NOT NULL, 
                        `energy` REAL NOT NULL, 
                        `fat` REAL NOT NULL, 
                        `carbohydrates` REAL NOT NULL, 
                        `sugar` REAL NOT NULL,
                        `fiber` REAL NOT NULL,
                        `protein` REAL NOT NULL,
                        `salt` REAL NOT NULL
                    )
                """.trimIndent())

                database.execSQL("""
                    INSERT INTO `favorites_new` (`ean`, `name`, `energy`, `fat`, `carbohydrates`, `sugar`, `fiber`, `protein`, `salt`)
                    SELECT `ean`, `name`, `energy`, `fat`, `carbohydrates`, `sugar`, `fiber`, `protein`, `salt`
                    FROM `favorites`
                """.trimIndent())

                database.execSQL("DROP TABLE IF EXISTS `favorites`")

                database.execSQL("ALTER TABLE `favorites_new` RENAME TO `favorites`")

            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Step 1: Create a new table with only the `ean` column
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `favorites_new` (
                        `ean` INTEGER PRIMARY KEY NOT NULL
                    )
                """.trimIndent())

                // Step 2: Copy the `ean` data from the old table to the new table
                database.execSQL("""
                    INSERT INTO `favorites_new` (`ean`)
                    SELECT `ean` FROM `favorites`
                """.trimIndent())

                // Step 3: Drop the old `favorites` table
                database.execSQL("DROP TABLE IF EXISTS `favorites`")

                // Step 4: Rename the new table to `favorites`
                database.execSQL("ALTER TABLE `favorites_new` RENAME TO `favorites`")
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
                        .addMigrations(MIGRATION_2_3)
                        .addMigrations(MIGRATION_3_4)
                        .addMigrations(MIGRATION_4_5)
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
