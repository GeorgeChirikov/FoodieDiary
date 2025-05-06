package com.example.foodiediary.views

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.ui.theme.FoodieDiaryTheme
import com.example.foodiediary.utils.CameraViewModelFactory


/**
 * This function displays the camera view using CameraX and allows the user to scan EAN-13 barcodes.
 * It uses a ViewModel to manage the camera state and handle barcode scanning.
 *
 * @param navController The NavController used for navigation.
 * @param modifier The modifier to be applied to the camera view.
 * @param showPopup A lambda function to show a popup with the scanned barcode.
 *
 * This function uses the CameraX library to display the camera view and scan EAN-13 barcodes.
 * It binds the camera lifecycle to the current lifecycle owner and sets up a preview view.
 * The button text is dynamically updated based on the scanning state and shows user if camera is scanning or not.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraView(
    navController: NavController,
    modifier: Modifier = Modifier,
    showPopup: (barcode: Long) -> Unit, // Function to show popup
) {
    val context = LocalContext.current


    val cameraController = LifecycleCameraController(context)
    cameraController.bindToLifecycle(LocalLifecycleOwner.current)
    cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    val viewmodel: CameraViewModel = viewModel(
        factory = CameraViewModelFactory(
            context = context,
            navController = navController,
            cameraController = cameraController,
            showPopup = showPopup
        )
    )

    val buttonText by viewmodel.buttonText.collectAsState()
    //val ean13Result by viewmodel.ean13Result.collectAsState()



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
                Button(
                    onClick = {
                        viewmodel.onScanEanButtonClick()

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

/**
 * This function is used to request camera permission and show the camera view.
 * It checks if the camera permission is granted, and if so, it displays the camera view.
 * @param navController The NavController used for navigation.
 * @param showPopup A lambda function to show a popup with the scanned barcode.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Prelude(navController: NavController ,showPopup: (barcode: Long) -> Unit) {
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    if (cameraPermission.status.isGranted) {
        CameraView(
            navController = navController,
            modifier = Modifier,
            showPopup = showPopup,
        )
    } else LaunchedEffect(true) {
        cameraPermission.launchPermissionRequest()
    }
}


@Preview(showBackground = true)
@Composable
fun CameraViewPreview() {
    val navController = rememberNavController()
    FoodieDiaryTheme {
        CameraView(
            navController = navController,
            showPopup = { /* No-op for preview */ }
        )
    }
}