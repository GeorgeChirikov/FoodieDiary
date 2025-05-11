# Camera View Documentation

## Overview
The **Camera View** in the Foodie Diary app allows users to scan EAN barcodes of food products using their device's camera. It integrates with Google's ML Kit for barcode recognition and provides a seamless user experience for capturing and processing barcodes.

---

## Features
- **Real-time Camera Preview**: Displays a live feed from the device's camera.
- **Barcode Scanning**: Detects and processes EAN13 barcodes using ML Kit.

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


3. **Data Storage**:
   - Scanned new barcodes open a form to save the item to the database.
   - Existing barcodes display a popup with item details.

---

## Navigation
The Camera View is accessible from:
- **Home View**: Tap the "Scan Barcode" button to open the Camera View.
- **Navigation Drawer**: Select "Scan EAN" to access the Camera View.

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

```

![Camera](/docs/images/camera.jpg)

![Form](/docs/images/form.jpg)