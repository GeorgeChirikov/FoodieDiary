# [Foodie Diary] - [Popup] UI Description

This document describes the user interface for the **[Popup]** within the **[Foodie Diary]** application.

## 1. Overview

Popup provides the user with item specific information. This information includes the EAN barcode,
the items name and nutrient facts. With the popup the user can make items their favorite, 
add them to diary and delete them.

## 2. Layout and Structure

*   **Dialog:**
    * Handles the popup card content and displays it on the screen 
*   **Popup Card:**
    *   Has all the item specific informations
    *   The X shaped close button, product name and EAN barcode are in a seperate row from nutrient facts
    *   Nutrient facts are on their own NutrientRows, which displays the nutrient and the nutrient amount in a clean way
    *   Buttons, inlcuding add to diary, remove from favorites or add to favorites and delete item, are displayed after the nutrient rows

## 3. UI Elements

*   **`Dialog` - Action Window:** Displays the popup content.
*   **`Card` - Content holder:** Contains information and actions about the item.
*   **`Column` - Content placer:** Places content vertically.
*   **`Row` - Key information:** Holds key information like item name and EAN barcode next to each other. Places the X icon nicely in the corner.
*   **`Text` - Key information:** Displays important information.
*   **`NutrientRow` - Nutrient facts:** Displays nutrient facts in a clear, readable and understandable way.
*   **`Button` - Many actions:** Can close the popup, add the item to the diary, add item to the favorites or remove item from favorites and delete item

## 4. Interaction Flows

*   **Tapping [X-icon]:** Closes the popup.
*   **Tapping [Add to Diary button]:** Adds the specific item to the user's diary.
*   **Tapping [Add to favorites/Remove from favorites]:** Either adds the item to favorites or removes it from favorites

## 5. Visual Design and Styling

*   **Color Palette:** ShyGreen(0xFFC5E1A3), IndigoPurple(0xFF8538b0) and RoseRed(0xFFBC4749)
*   **Typography:** Default android font
*   **Spacing and Layout:** Spaces between elements have 16.dp height except for one that is 4.dp. NutrientRow has 5.dp at the start and the end
*   **Icons:** Icons.Filled.Close

## 6. States

*   **Empty State:** When there is no item data, it displays a text "No item found."

## 7. Screenshots (Optional but Recommended)

![popupView](/docs/images/popup.png)
