package com.example.foodiediary.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodiediary.viewmodels.PopUpViewModel

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