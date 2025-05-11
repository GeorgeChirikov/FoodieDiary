# Navigation Bar Documentation

## Overview
The **Navigation Bar** in the Foodie Diary app provides users with a convenient way to navigate between different sections of the app. It is implemented as a drawer navigation menu accessible from the Home View.

---

## Features
- **Drawer Menu**: A side menu that slides out, listing all available views.
- **Quick Access**: Allows users to switch between the Home, Diary, Favorites, Barcode Scanner, and Search views.

---

## Implementation Details
- **Architecture**: MVVM
  - **View**: Composable screen for the navigation drawer UI.
  - **ViewModel**: Manages navigation actions and tracks the active view.
  - **Model**: No specific model is required for navigation.

---

## Key Components
1. **Drawer Menu**:
   - Lists all available views: Home, Diary, Favorites, Barcode Scanner, and Search.
   - Each menu item is clickable and navigates to the corresponding view.

3. **Hamburger Icon**:
   - Located in the top-left corner of the app, it opens the navigation drawer.

---

## Navigation
The Home View is the default screen when the app is launched. From drawer navigation, users can access:
- **Home View**: Main dashboard with nutritional summary and quick access buttons.
- **Barcode Scanner**: Opens the camera view for scanning barcodes.
- **Search**: Opens the search view for finding food items.
- **Favorites**: Displays a list of favorite items.
- **Diary**: Displays the diary view for tracking daily food intake.

---

![NavBar](/docs/images/navbar.png)