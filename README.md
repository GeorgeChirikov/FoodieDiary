<h2>Foodie Diary - Your Personal Food Journal</h2>
an ICT engineering project

****
<h3>Project Description</h3>

****
**Foodie Diary** Foodie Diary is an Android application that allows users to scan EAN barcodes of food products, store them locally, and manage favorites or daily food logs. It provides a simple and efficient interface for tracking what users consume.



****

<h3>Technology utilized</h3>

****

This app was developed using Kotlin in Android Studio, and it follows the Model-View-ViewModel (MVVM) architectural pattern. The user interface is built with Jetpack Compose.

The application uses the device's camera together with Google's ML Kit to scan and recognize barcodes (EAN13 codes). Data is stored locally using Room database.

****

<h3>Features</h3>

****

- 📷 EAN Barcode Scanning (ML Kit)

- 🏠 Home View

- 🔍 Search View

- 📘 Diary View

- ❤️ Favorites View

- 📸 Camera View (CameraX)

****

<h3>Views</h3>

****

<h3>HomeView</h3>

![homeView1](/docs/images/home1.png)
![homeView2](/docs/images/home2.png)

<h3>Navigation Drawer</h3>

![navigationDrawer](/docs/images/navbar.png)

<h3>CameraView</h3>

![cameraView](/docs/images/camera.png)

<h3>SearchView</h3>

![searchView](/docs/images/search.png)

<h3>FavoritesView</h3>

![favoritesView](/docs/images/favorites.png)

<h3>DiaryView</h3>

![diaryView](/docs/images/diary.png)

****

<h3>Tech Stack</h3>

****

- Language: Kotlin

- Architecture: MVVM

- UI: Jetpack Compose

- Local Storage: Room Database

- Camera & Barcode:

    - ML Kit (for barcode scanning)

    - CameraX (for camera functionality)

****

<h3>Architecture</h3>

****

- Model: Handles data operations (Room database, repositories).

- View: Jetpack Compose screens for user interaction.

- ViewModel: Acts as a bridge between the Model and View, managing UI-related data.

- MainActivity: Entry point of the app, initializes the navigation graph.

- Utils: Contains utility classes for injecting parameters into the ViewModels

```
com.example.foodiediary
├── models/
│   └── data/
│       ├── dao/          # Interfaces for Room DB access
│       ├── database/     # Room Database initialization
│       ├── entity/       # Room Entities
│       └── repository/   # Repository classes for data access
│
├── utils/                # Utility classes (e.g., barcode helpers, converters)
├── viewmodels/           # ViewModels for business logic & UI interaction
├── views/                # UI: Compose screens, Activities, Fragments, Adapters
└── MainActivity.kt       # Main app entry point

```

****

<h3>Key Components</h3>

****

<h4>Barcode Scanner</h4>

- Library: ML Kit Barcode Scanning

- Integration: Connected to CameraX preview and processes barcodes on frame analysis.

<h4>Database</h4>

- Room Entities:

    - Item: stores scanned product info (EAN, name, etc.)

    - Added: links product to date entries

    - Favorite: stores user’s favorite products

- DAO interfaces:

    - ItemDao, AddedDao, FavoriteDao

- Repository Pattern used to abstract data access from ViewModels.

- Migration strategy for database versioning.
    - Database versioning is handled by Room, which automatically manages migrations when the database schema changes. The app uses a simple migration strategy to ensure data integrity and continuity.

``` Kotlin
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

<h4>Views</h4>

- Implemented with Jetpack Compose

- Each feature is in its own Composable screen

- Navigation is managed in MainActivity


****

<h3>Build & Run</h3>

****













****

<h3>Documents</h3>

****
TO DO:

- **User manual** is located in the directory `docs/UserManual.md`
- **Sprint documentation** is located in the directory `docs/SprintProcess.md`
- **Product backlog** is located in the directory `docs/ProductBacklog.md`

****

<h3>Links:</h3>

****

- to be added later if needed

****
<h3>Database design</h3>

- to be added later


