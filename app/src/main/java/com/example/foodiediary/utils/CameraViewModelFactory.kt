package com.example.foodiediary.utils

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.foodiediary.viewmodels.CameraViewModel

@Suppress("UNCHECKED_CAST")
class CameraViewModelFactory (
    private val context: Context,
    private val navController: NavController,
    private val cameraController: LifecycleCameraController,
    private val showPopup: (barcode: Long) -> Unit
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            return CameraViewModel(
                context,
                navController,
                cameraController,
                showPopup
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}