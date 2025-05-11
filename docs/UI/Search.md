# [Foodie Diary] - [Search] UI Documentation

This document describes the user interface for the **[Search]** within the **[Foodie Diary]** application.

## 1. Overview

Search allows the user to look for items by their names. It displays at most five items that match the user's input the most.

## 2. Layout and Structure

*   **SearchBar:**
    * Takes the user's input as query 
*   **Lazy Column:**
    *   Displays the search results that match the user's input the most

## 3. UI Elements

*   **`Column` - Content placer:** Places content vertically.
*   **`Box` - Content holder:** Holds SearchBar and result view
*   **`SearchBar` - Query input:** Allows user to type a query and finds results
*   **`LazyColumn` - Displays results:** Displays the top results as a vertically scrollable view

## 4. Interaction Flows

*   **Tapping [SearchBar]:** Opens the device's keyboard and the list where results are displayed and allows the user to type
*   **Tapping [Gradient background]:** Closes the result view.
*   **Tapping [Outside of keyboard]:** Closes keyboard.

## 5. Visual Design and Styling

*   **Color Palette:** AppleRed(0xFFE87461), LightGreen(0xFFA1CF6B), GrassGreen(0xFF7AC74F) and default white
*   **Typography:** Default android font
*   **Spacing and Layout:** Column and SearchBar padding is 16.dp. Elements are fillMaxSize()
*   **Icons:** No icons

## 6. States

*   **Start state:** Search bar is small and there is no view for results.
*   **Clicked state:** Search bar appears large and there is a view for results.
*   **Empty State:** When there is no item data, the result view is simply white.

## 7. Screenshots

![searchView1](/docs/images/search1.png)
![searchView2](/docs/images/search2.png)
