package com.example.foodiediary.viewmodels

import android.content.Context
import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.repository.ItemRepository
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.text.toLong

class CameraViewModel(
    private val context:Context,
    private val navController: NavController,
    private val cameraController: LifecycleCameraController,
    showPopup: (barcode: Long) -> Unit
) : ViewModel() {

    private val scannerOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        .build()
    private var scanner: BarcodeScanner = BarcodeScanning.getClient(scannerOptions)
    var isScanning = false
    private val itemRepository: ItemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())

    //val ean13Result = MutableStateFlow<String>("No EAN Code")

    val scanningDelay = 10_000L // 10 seconds
    private val coroutineScope = viewModelScope

    val buttonText = MutableStateFlow("Scan EAN")

    // ensure that the database operations are thread-safe
    private val databaseMutex = Mutex()

    fun onScanEanButtonClick() {
        if (!isScanning){
            Log.d("CameraView", "Scan EAN button clicked")
            isScanning = true
            cameraController.setImageAnalysisAnalyzer(
                context.mainExecutor,
                imageAnalyzer
            )

            // Stop scanning after 10 seconds
            coroutineScope.launch {
                for (i in 1..(scanningDelay / 1000).toInt()) {
                    delay(1000)
                    if (isScanning) {
                        buttonText.value = "Scanning" + ".".repeat(i % 4)
                        Log.d("CameraView", "$buttonText $i seconds passed")
                    }
                }
                if (isScanning) {
                    stopScanning()
                    Log.d("CameraView", "Scanning timeout")
                }
            }
        }

    }

    private val imageAnalyzer = ImageAnalysis.Analyzer { imageProxy ->
        //Log.d("CameraView", "Image analysis started")
        scanEan(imageProxy) { ean13Code ->
            Log.d("CameraView", "inside scanEan(imageProxy)")
            if (ean13Code != null) {
                Log.d("CameraView", "Yes EAN code detected")
                //ean13Result.value = ean13Code
                handleScannedCode(ean13Code, showPopup)
            } else {
                Log.d("CameraView", "No EAN code detected")
            }
        }
    }

    fun handleScannedCode(ean13Code: String, showPopup: (barcode: Long) -> Unit) {
        viewModelScope.launch {
            // Perform database operations in a thread-safe manner
            databaseMutex.withLock {
                val eanLong = ean13Code.toLong()
                val existingItem = itemRepository.getItemByEan(eanLong)
                if (existingItem == null) {
                    navController.navigate("formView/$ean13Code")
                    Log.d("CameraViewModel", "FormView opened with EAN $ean13Code")
                } else {
                    showPopup(eanLong)
                    Log.d("CameraViewModel", "Item with EAN $ean13Code already exists in the database")
                }
            }
            // Stop scanning after handling the scanned code
            stopScanning()
            // Reset the scanner to allow for new scans
            resetScanner()
        }
    }

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    fun scanEan(imageProxy: ImageProxy, onResult: (String?) -> Unit) {
        if (!isScanning) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val ean13Code = barcodes.firstOrNull()?.displayValue
                    onResult(ean13Code)
                    //Log.d("CameraViewModel_onSuccess", "EAN 13 code: $ean13Code")
                }
                .addOnFailureListener {
                    onResult(null) // Handle failure
                    Log.d("CameraViewModel_onFailure", "Failed to scan barcode: ${it.message}")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                    //Log.d("CameraViewModel_onComplete", "ImageProxy closed")
                }
        } else {
            imageProxy.close()
        }
    }

    fun stopScanning() {
        cameraController.clearImageAnalysisAnalyzer()

        isScanning = false
        buttonText.value = "Scan EAN"
        //ean13Result.value = "No EAN Code"
        Log.d("CameraViewModel", "Scanning stopped")
    }

    fun resetScanner() {
        isScanning = false
        buttonText.value = "Scan EAN"
        //ean13Result.value = "No EAN Code"

        scanner.close()
        scanner = BarcodeScanning.getClient(scannerOptions)

        cameraController.clearImageAnalysisAnalyzer()
        cameraController.setImageAnalysisAnalyzer(context.mainExecutor, imageAnalyzer)
        Log.d("CameraViewModel", "Scanner reset")
    }
}