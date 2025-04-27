package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.entity.Added
import com.example.foodiediary.models.data.entity.Item
import com.example.foodiediary.models.data.repository.AddedRepository
import com.example.foodiediary.models.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PopUpViewModel(context: Context) : ViewModel() {

    private val itemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())
    private val addedRepository = AddedRepository(AppDatabase.getInstance(context).addedDao())

    private val _barcodeItem = MutableStateFlow(Item(0,"",0.0,0.0,0.0,0.0,0.0,0.0,0.0))
    val barcodeItem = _barcodeItem.asStateFlow()

    fun getBarcodeData(barcode: Long) {
        viewModelScope.launch {
            val item = itemRepository.getItemByEan(barcode)
            _barcodeItem.value = item ?: Item(0,"",0.0,0.0,0.0,0.0,0.0,0.0,0.0)
        }
    }

    fun addItemToFavorites(added: Added) {
        viewModelScope.launch {
            addedRepository.insert(added)
        }
    }

}