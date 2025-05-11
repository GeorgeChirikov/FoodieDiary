# Home View Documentation

## Overview
The **Home View** in the Foodie Diary app serves as the main dashboard, providing users with an overview of their daily food intake and quick access to key features like the Barcode scanner, Diary and Favorites views.

---

## Features
- **Daily Nutritional Summary**: Displays total calories, protein, fat, and carbohydrates consumed for the current day.
- **Quick scanner Navigation**: Provides button to open the scanner for barcode scanning.
- **Navigation**: Drawer navigation to access all views of the app.
- **Items**: Displays a list of items added to the local database.
- **Favorites**: Displays a list of favorite items added to the local database to quickly access them.

---

## Implementation Details
- **Architecture**: MVVM
  - **View**: Composable screen for the home UI.
  - **ViewModel**: Manages the nutritional summary and handles navigation actions.
  - **Model**: Retrieves data from the Room database.

---

## Key Components
1. **Nutritional Summary**:
   - Displays total energy, protein, fat, and carbohydrates for the selected date.
   - Updates dynamically when new entries are added to the diary.

2. **Scanner Button**:
   - Opens the camera view for barcode scanning.
   - Allows users to quickly add items to their diary.

3. **Navigation Drawer**:
   - Provides access to different views of the app (Diary, Favorites, Scanner).
   - Allows users to switch between different sections of the app easily.

4. **Items List**:
   - Displays a list of items added to the local database.
   - Each item is clickable, allowing users to view details or add them to the diary.

5. **Favorites List**:
   - Displays a list of favorite items added to the local database.
   - Each item is clickable, allowing users to view details or add them to the diary.

---

![Home](/docs/images/home1.png)
![Home](/docs/images/home2.png)