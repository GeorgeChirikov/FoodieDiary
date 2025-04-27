package com.example.foodiediary.views

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.foodiediary.viewmodels.CameraViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodiediary.models.data.entity.Item
import com.example.foodiediary.viewmodels.DatabaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraView(
    modifier: Modifier = Modifier,
    showPopup: (barcode: Long) -> Unit, // Function to show popup
) {
    val context = LocalContext.current
    val viewmodel: CameraViewModel = viewModel()

    val cameraController = LifecycleCameraController(context)
    cameraController.bindToLifecycle(LocalLifecycleOwner.current)
    cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    var buttonText by remember { mutableStateOf("Scan EAN") }
    var ean13Result by remember { mutableStateOf<String>("No EAN Code") }
    var isScanning by remember { mutableStateOf(false) }
    val scanningDelay = 10_000L // 10 seconds

    val dbViewModel = DatabaseViewModel(context)

    val coroutineScope = rememberCoroutineScope()

    val imageAnalyzer = ImageAnalysis.Analyzer { imageProxy ->
        viewmodel.onScanEAN(
            imageProxy = imageProxy,
            onResult = { ean13Code ->
                if (ean13Code != null) {
                    Log.d("CameraView", "EAN 13 code returned: $ean13Code")
                    cameraController.clearImageAnalysisAnalyzer()
                    ean13Result = "EAN: $ean13Code inserted into the database"
                    buttonText = "Scan EAN"
                    isScanning = false
                    viewmodel.isScanning = false
                    viewmodel.reset()
                    coroutineScope.launch {
                        if (dbViewModel.getItemByEan(ean13Code.toLong()) == null) {
                            dbViewModel.insertItem(
                                Item(
                                    ean = ean13Code.toLong(),
                                    name = "Item Name",
                                    energy = 100.0,
                                    protein = 10.0,
                                    fat = 5.0,
                                    carbohydrates = 20.0,
                                    sugar = 10.0,
                                    fiber = 5.0,
                                    salt = 0.5
                                )
                            )
                            Log.d("CameraView", "Item with EAN $ean13Code inserted into the database")
                        } else {
                            Log.d("CameraView", "Item with EAN $ean13Code already exists in the database")
                            ean13Result = "EAN: $ean13Code found in the database"
                            showPopup(ean13Code.toLong())
                        }
                    }
                }
            }
        )
    }

    Scaffold(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.BottomCenter
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    val preview = PreviewView(context)

                    preview.controller = cameraController
                    preview
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom

            ) {
                Text(
                    text = ean13Result,
                    modifier = Modifier
                        .padding(12.dp)
                        .background(
                            color = Color(0xBFFFFFFF),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                )
                Button(
                    onClick = {
                        if (!isScanning) {
                            Log.d("CameraView", "Scan EAN button clicked")
                            buttonText = "Scanning..."
                            isScanning = true
                            viewmodel.isScanning = true
                            cameraController.setImageAnalysisAnalyzer(
                                context.mainExecutor,
                                imageAnalyzer
                            )

                            // Stop scanning after 10 seconds
                            coroutineScope.launch {
                                for (i in 1..(scanningDelay/1000).toInt()) {
                                    delay(1000)
                                    if (isScanning) {
                                        buttonText = "Scanning" + ".".repeat(i % 4)
                                        Log.d("CameraView", "$buttonText $i seconds passed")
                                    }
                                }
                                if (isScanning) {
                                    cameraController.clearImageAnalysisAnalyzer()
                                    buttonText = "Scan EAN"
                                    isScanning = false
                                    viewmodel.isScanning = false
                                    viewmodel.reset()
                                    ean13Result = "No EAN Code"
                                    Log.d("CameraView", "Scanning timeout")
                                }
                            }
                        }

                    },
                    modifier = Modifier
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),

                    ) {
                    Text(text = buttonText)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Prelude(showPopup: (barcode: Long) -> Unit) {
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    if (cameraPermission.status.isGranted) {
        CameraView(
            modifier = Modifier,
            showPopup = showPopup,
        )
    } else LaunchedEffect(true) {
        cameraPermission.launchPermissionRequest()
    }
}

@Composable
@Preview
fun CameraViewPreview() {
    Prelude {}
}