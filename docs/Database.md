# Database Documentation

## Overview
The **Foodie Diary** app uses a local SQLite database managed by the Room persistence library. The database stores information about scanned food products, user favorites, and daily food logs. It follows the Repository pattern to abstract data access and ensure a clean architecture.

---

## Database Structure
The database consists of the following entities:

### 1. **Item**
Stores information about scanned food products.

| Column          | Type    | Description                  |
|-----------------|---------|------------------------------|
| `ean`           | INTEGER | Product Barcode / ID         |
| `name`          | TEXT    | Name of the product          |
| `energy`        | REAL    | Energy content (kcal)        |
| `fat`           | REAL    | Fat content (grams)          |
| `carbohydrates` | REAL    | Carbohydrate content (grams) |
| `sugar`         | REAL    | Sugar content (grams)        |
| `fiber`         | REAL    | Fiber content (grams)        |
| `protein`       | REAL    | Protein content (grams)      |
| `salt`          | REAL    | Salt content (grams)         |

---

### 2. **Added**
Links products to specific dates in the user's food diary.

| Column      | Type    | Description                |
|-------------|---------|----------------------------|
| `Timestamp` | TEXT    | Date of the entry / ID     |
| `ean`       | INTEGER | EAN barcode of the product |

---

### 3. **Favorite**
Stores the user's favorite products.

| Column      | Type    | Description              |
|-------------|---------|--------------------------|
| `ean`       | INTEGER | Product Barcode / ID     |

---

## DAO Interfaces
The following DAO interfaces are used to interact with the database:

### **ItemDao**
- `insert(item: Item)`: Inserts a new product.
- `getAllItems()`: Retrieves all products.
- `getItemByEan(ean: Long)`: Fetches a product by its EAN barcode.

### **AddedDao**
- `insert(added: Added)`: Adds a product to the diary.
- `getAllAdded()`: Retrieves all diary entries.
- `getAddedByEan(ean: Int)`: Fetches diary entries by EAN barcode.
- `getItemsSortedByTimestamp()`: Retrieves diary entries sorted by timestamp.
- `getItemByTimestamp(timestamp: String)`: Fetches a diary entry by its timestamp.
- `deleteByEan(ean: Int)`: Removes a diary entry by EAN barcode.
- `deleteByTimestamp(timestamp: String)`: Removes a diary entry by timestamp.

### **FavoriteDao**
- `insert(favorite: Favorite)`: Adds a product to favorites.
- `getAllFavorites()`: Retrieves all favorite products.
- `deleteFavoriteByEan(ean: Int)`: Removes a product from favorites.

---

## Repository Pattern
Repositories are used to abstract data access and provide a clean API for the ViewModels. Each repository interacts with its corresponding DAO.

### Example: `ItemRepository`
```kotlin
class ItemRepository(private val itemDao: ItemDao) {
    suspend fun insertItem(item: Item) = itemDao.insertItem(item)
    fun getAllItems() = itemDao.getAllItems()
    fun getItemByEan(ean: Int) = itemDao.getItemByEan(ean)
}
```

## Migration Strategy

The app uses Room's built-in migration strategy to handle database versioning. When the database schema changes, Room automatically manages migrations to ensure data integrity and continuity.

### Example Migration
```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        // Drop the old table users
        db.execSQL("DROP TABLE IF EXISTS `users`")

        // Create the new table for favorites
        db.execSQL("""
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
```

### Example Migration 1 to 2 adding to database building

```kotlin
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
```

#### All migrations have to be kept and added to the database building to insure that the database is up to date and all data is kept even if the app is updated from much older version!

