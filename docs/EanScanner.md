# EAN Scanner Documentation

## Overview
The **EAN Scanner** in the Foodie Diary app allows users to scan EAN13 barcodes of food products using their device's camera. It integrates with Google's ML Kit for barcode recognition and provides a seamless experience for capturing and processing barcodes.

---

## Features
- **Real-time Barcode Scanning**: Detects and processes EAN13 barcodes in real-time.
- **Product Lookup**: Matches scanned barcodes with existing products in the local database.
- **Add New Product**: Prompts users to add details for unrecognized barcodes.

---

## Implementation Details
- **Library**: ML Kit Barcode Scanning
- **Camera Framework**: CameraX
- **Architecture**: MVVM
  - **View**: Only for CameraView
  - **ViewModel**: Handles barcode processing and updates the UI state.
  - **Model**: Stores scanned barcode data in the Room database.

---

## Key Components
1. **Camera Preview**:
   - Utilizes CameraX for a live camera feed.
   - Configured to optimize for barcode scanning.

2. **Barcode Detection**:
   - ML Kit processes frames from the camera feed.
   - Detects EAN13 barcodes and extracts relevant data.

3. **Data Handling**:
   - If the barcode matches an existing product, its details are displayed.
   - If the barcode is new, users are prompted to add the product to the database.

---

## Navigation
The EAN Scanner (Camera view) is accessible from:
- **Home View**: Tap the "Scan Barcode" button to open the scanner.
- **Navigation Drawer**: Select "Scan EAN" to access the scanner.

---

## Code Snippet
Here’s an example of how the EAN Scanner is implemented:

```kotlin
private val scannerOptions = BarcodeScannerOptions.Builder()
    .setBarcodeFormats(Barcode.FORMAT_EAN_13)
    .build()
private var scanner: BarcodeScanner = BarcodeScanning.getClient(scannerOptions)
```

```kotlin
scanner.process(image)
    .addOnSuccessListener { barcodes ->
        val ean13Code = barcodes.firstOrNull()?.displayValue
        onResult(ean13Code)
    }
    .addOnFailureListener {
        onResult(null) // Handle failure
        Log.d("CameraViewModel_onFailure", "Failed to scan barcode: ${it.message}")
    }
    .addOnCompleteListener {
        imageProxy.close()
    }
```