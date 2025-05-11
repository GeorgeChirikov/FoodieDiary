# Camera View Documentation

## Overview
The **Camera View** in the Foodie Diary app allows users to scan EAN barcodes of food products using their device's camera. It integrates with Google's ML Kit for barcode recognition and provides a seamless user experience for capturing and processing barcodes.

---

## Features
- **Real-time Camera Preview**: Displays a live feed from the device's camera.
- **Barcode Scanning**: Detects and processes EAN13 barcodes using ML Kit.
- **User Feedback**: Highlights detected barcodes and provides visual feedback.
- **Error Handling**: Displays messages for invalid or unrecognized barcodes.

---

## Implementation Details
- **Library**: ML Kit Barcode Scanning
- **Camera Framework**: CameraX
- **Architecture**: MVVM
  - **View**: Composable screen for the camera UI.
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

3. **UI Feedback**:
   - Highlights detected barcodes in the camera preview.
   - Displays a success message when a barcode is scanned.

4. **Data Storage**:
   - Scanned barcodes are saved in the Room database for future reference.

---

## Navigation
The Camera View is accessible from:
- **Home View**: Tap the camera icon to open the Camera View.

---

## Code Snippet
Here’s an example of how the Camera View is implemented:

```kotlin
@Composable
fun CameraView(viewModel: CameraViewModel) {
    val barcodeResult by viewModel.barcodeResult.observeAsState()

    CameraPreview(
        onBarcodeDetected = { barcode ->
            viewModel.processBarcode(barcode)
        }
    )

    barcodeResult?.let {
        Text(text = "Scanned: ${it.ean}")
    }
}