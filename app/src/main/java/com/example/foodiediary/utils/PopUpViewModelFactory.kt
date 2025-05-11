package com.example.foodiediary.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodiediary.viewmodels.PopUpViewModel

/**
 * ViewModelFactory is for injecting parameters into the ViewModel.
 * It is used to create an instance of the ViewModel with the required parameters.
 *
 * @param context The context of the application.
 */
@Suppress("UNCHECKED_CAST")
class PopUpViewModelFactory (
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopUpViewModel::class.java)) {
            return PopUpViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}