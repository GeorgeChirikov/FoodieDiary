package com.example.foodiediary.viewmodels

import android.content.Context
import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.entity.Item
import com.example.foodiediary.models.data.repository.ItemRepository
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.text.toLong

class CameraViewModel(
    private val context:Context,
    private val cameraController: LifecycleCameraController,
    showPopup: (barcode: Long) -> Unit) : ViewModel() {

    private val scannerOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        .build()
    private var scanner: BarcodeScanner = BarcodeScanning.getClient(scannerOptions)
    var isScanning = false
    private val itemRepository: ItemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())

    val ean13Result = MutableStateFlow<String>("No EAN Code")

    val scanningDelay = 10_000L // 10 seconds
    private val coroutineScope = viewModelScope

    val buttonText = MutableStateFlow("Scan EAN")

    // ensure that the database operations are thread-safe
    private val databaseMutex = Mutex()

    fun onScanEanButtonClick() {
        if (!isScanning) {
            Log.d("CameraView", "Scan EAN button clicked")
            buttonText.value = "Scanning..."
            isScanning = true
            cameraController.setImageAnalysisAnalyzer(
                context.mainExecutor,
                imageAnalyzer
            )

            // Stop scanning after 10 seconds
            coroutineScope.launch {
                for (i in 1..(scanningDelay/1000).toInt()) {
                    delay(1000)
                    if (isScanning) {
                        buttonText.value = "Scanning" + ".".repeat(i % 4)
                        Log.d("CameraView", "$buttonText $i seconds passed")
                    }
                }
                if (isScanning) {
                    cameraController.clearImageAnalysisAnalyzer()
                    buttonText.value = "Scan EAN"
                    isScanning = false
                    reset()
                    ean13Result.value = "No EAN Code"
                    Log.d("CameraView", "Scanning timeout")
                }
            }
        }
    }

    val imageAnalyzer = getImageAnalyzer { ean13Code ->
        if (ean13Code != null && isScanning) {
            isScanning = false
            handleScannedCode(ean13Code, showPopup)
        }
    }

    fun handleScannedCode(ean13Code: String, showPopup: (barcode: Long) -> Unit) {
        viewModelScope.launch {
            // Perform database operations in a thread-safe manner
            databaseMutex.withLock {
                val eanLong = ean13Code.toLong()
                val existingItem = itemRepository.getItemByEan(eanLong)
                if (existingItem == null) {
                    itemRepository.insert(
                        Item(
                            ean = eanLong,
                            name = "Item Name",
                            energy = 100.0,
                            protein = 10.0,             // Tänne Maisa tulee se FormsView navigaatio :)
                            fat = 5.0,
                            carbohydrates = 20.0,
                            sugar = 10.0,
                            fiber = 5.0,
                            salt = 0.5
                        )
                    )
                    Log.d("CameraViewModel", "Item with EAN $ean13Code inserted into the database")
                } else {
                    Log.d(
                        "CameraViewModel",
                        "Item with EAN $ean13Code already exists in the database"
                    )
                    showPopup(eanLong)
                }
            }
            isScanning = false
            buttonText.value = "Scan EAN"
            cameraController.clearImageAnalysisAnalyzer()
        }
    }

    fun getImageAnalyzer(
        onResult: (ean13Code: String?) -> Unit
    ): ImageAnalysis.Analyzer {
        return ImageAnalysis.Analyzer { imageProxy ->
            scanEan(
                imageProxy = imageProxy,
                onResult = { ean13Code ->
                    if (ean13Code != null) {
                        Log.d("CameraViewModel", "EAN 13 code returned: $ean13Code")
                        ean13Result.value = "EAN: $ean13Code"
                        onResult(ean13Code)
                    }
                }
            )
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

    fun reset(){
        scanner.close()
        scanner = BarcodeScanning.getClient(scannerOptions)
        isScanning = false
    }
}