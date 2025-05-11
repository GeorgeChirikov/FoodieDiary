package com.example.foodiediary.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodiediary.viewmodels.DiaryViewModel

/**
 * ViewModelFactory is for injecting parameters into the ViewModel.
 * It is used to create an instance of the ViewModel with the required parameters.
 *
 * @param context The context of the application.
 */
@Suppress("UNCHECKED_CAST")
class DiaryViewModelFactory (
    private val context: Context
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
            return DiaryViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}