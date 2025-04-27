package com.example.foodiediary.views

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.CameraSelector
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
import com.example.foodiediary.utils.CameraViewModelFactory
import com.example.foodiediary.utils.PopUpViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraView(
    modifier: Modifier = Modifier,
    showPopup: (barcode: Long) -> Unit, // Function to show popup
) {
    val context = LocalContext.current
    val viewmodel: CameraViewModel = viewModel(
        factory = CameraViewModelFactory(context = context)
    )

    val cameraController = LifecycleCameraController(context)
    cameraController.bindToLifecycle(LocalLifecycleOwner.current)
    cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    var buttonText by remember { mutableStateOf("Scan EAN") }
    var ean13Result by remember { mutableStateOf<String>("No EAN Code") }
    var isScanning by remember { mutableStateOf(false) }
    val scanningDelay = 10_000L // 10 seconds

    val coroutineScope = rememberCoroutineScope()

    val imageAnalyzer = viewmodel.getImageAnalyzer { ean13Code ->
        if (ean13Code != null) {
            viewmodel.handleScannedCode(ean13Code, showPopup)
        }
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