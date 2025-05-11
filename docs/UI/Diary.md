# Diary View Documentation

## Overview
The **Diary View** in the Foodie Diary app allows users to track their daily food intake. It displays a list of food items added to the diary for a specific date, along with their nutritional information. Users can add or remove entries and view their daily nutritional summary in home view.

---

## Features
- **Daily Food Log**: Displays food items added to the diary for the selected date.
- **Nutritional Summary**: Updates total calories, protein, fat, and carbohydrates consumed in home view.
- **Date Selection**: Enables users to switch between dates to view past or future entries.
- **Add**: Updates the diary with new food items when added to it from popup.

---

## Implementation Details
- **Architecture**: MVVM
  - **View**: Composable screen for the diary UI.
  - **ViewModel**: Manages diary entries and nutritional calculations.
  - **Model**: Stores diary data in the Room database.

---

## Key Components
1. **Diary List**:
   - Displays a list of food items added for the selected date.
   - Each item shows the name, and nutritional values.

2. **Nutritional Summary**:
   - Calculates and displays the total energy, protein, fat, and carbohydrates for the day.

3. **Date Picker**:
   - Allows users to select a specific date to view or modify diary entries.

---

## Navigation
The Diary View is accessible from:
- **Home View**: From the navigation drawer, select "Diary" to access the Diary View.

---

![Diary](/docs/images/diary.png)