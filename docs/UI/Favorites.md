# Favorites View Documentation

## Overview
The **Favorites View** in the Foodie Diary app allows users to manage their favorite food products. It displays a list of items marked as favorites, enabling users to quickly access frequently used products.

---

## Features
- **Favorites List**: Displays all products marked as favorites.
- **Quick Access**: Allows users to navigate to detailed product information or add items to the diary.
- **Remove Favorites**: Enables users to remove items from the favorites list via popup.

---

## Implementation Details
- **Architecture**: MVVM
  - **View**: Composable screen for the favorites UI.
  - **ViewModel**: Manages the list of favorite items and handles user actions.
  - **Model**: Stores favorite data in the Room database.

---

## Key Components
1. **Favorites List**:
   - Displays a list of favorite products with their names and nutritional values.
   - Each item is clickable, allowing users to navigate to the product's details (popup) or add it to the diary.

---

## Navigation
The Favorites View is accessible from:
- **Home View**: From the navigation drawer, select "Favorites" to access the Favorites View.

---

![Favorites](/docs/images/favorites.png)