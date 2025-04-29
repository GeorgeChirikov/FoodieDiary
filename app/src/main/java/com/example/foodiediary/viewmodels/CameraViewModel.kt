package com.example.foodiediary.viewmodels

import android.content.Context
import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.text.toLong

class CameraViewModel(context:Context) : ViewModel() {
    private val scannerOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        .build()
    private var scanner: BarcodeScanner = BarcodeScanning.getClient(scannerOptions)
    var isScanning = false
    private val itemRepository: ItemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())

    private val _ean13Result = MutableStateFlow<String>("No EAN Code")
    val ean13Result: StateFlow<String> = _ean13Result

    fun handleScannedCode(ean13Code: String, showPopup: (barcode: Long) -> Unit) {
        viewModelScope.launch {
            val eanLong = ean13Code.toLong()
            if (itemRepository.getItemByEan(eanLong) == null) {
                itemRepository.insert(
                    Item(
                        ean = eanLong,
                        name = "Item Name",  // tähän lomake :)
                        energy = 100.0,
                        protein = 10.0,
                        fat = 5.0,
                        carbohydrates = 20.0,
                        sugar = 10.0,
                        fiber = 5.0,
                        salt = 0.5
                    )
                )
                Log.d("CameraViewModel", "Item with EAN $ean13Code inserted into the database")
            } else {
                Log.d("CameraViewModel", "Item with EAN $ean13Code already exists in the database")
                showPopup(eanLong)
            }
        }
    }

    fun getImageAnalyzer(
        onResult: (ean13Code: String?) -> Unit
    ): ImageAnalysis.Analyzer {
        return ImageAnalysis.Analyzer { imageProxy ->
            onScanEAN(
                imageProxy = imageProxy,
                onResult = { ean13Code ->
                    if (ean13Code != null) {
                        Log.d("CameraViewModel", "EAN 13 code returned: $ean13Code")
                        _ean13Result.value = "EAN: $ean13Code"
                        onResult(ean13Code)
                    }
                }
            )
        }
    }

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    fun onScanEAN(imageProxy: ImageProxy, onResult: (String?) -> Unit) {
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