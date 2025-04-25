package com.example.foodiediary.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiediary.models.data.entity.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PopUpViewModel(private val db: DatabaseViewModel) : ViewModel() {

    private val _barcodeItem = MutableStateFlow(Item(0,"",0.0,0.0,0.0,0.0,0.0,0.0,0.0))
    val barcodeItem = _barcodeItem.asStateFlow()

    fun getBarcodeData(barcode: Long) {
        viewModelScope.launch {
            val item = db.getItemByEan(barcode)
            _barcodeItem.value = item ?: Item(0,"",0.0,0.0,0.0,0.0,0.0,0.0,0.0)
        }
    }

}