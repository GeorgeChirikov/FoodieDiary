package com.example.foodiediary.viewmodels

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class CameraViewModel : ViewModel() {
    private val scannerOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        .build()
    private val scanner = BarcodeScanning.getClient(scannerOptions)




    @OptIn(ExperimentalGetImage::class)
    fun onScanEAN(imageProxy: ImageProxy, onResult: (String?) -> Unit) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val ean13Code = barcodes.firstOrNull()?.displayValue
                    onResult(ean13Code) // Pass the scanned EAN-13 code
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


}

/*
private class ImageAnalyzer(val scanner: BarcodeScanner, EAN13: (String?) -> String? ) : ImageAnalysis.Analyzer {

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val result = scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val bounds = barcode.boundingBox
                        val corners = barcode.cornerPoints

                        val rawValue = barcode.rawValue

                        val valueType = barcode.valueType
                        // See API reference for complete list of supported types
                        when (valueType) {
                            Barcode.TYPE_PRODUCT -> {
                                val ean13Code = barcode.displayValue
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    // ...
                }
        }
    }
}

 */
