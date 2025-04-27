package com.example.foodiediary.viewmodels

import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class CameraViewModel : ViewModel() {
    private val scannerOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        .build()
    private var scanner: BarcodeScanner = BarcodeScanning.getClient(scannerOptions)
    var isScanning = false

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