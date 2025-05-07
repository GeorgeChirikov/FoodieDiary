package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.entity.Item
import com.example.foodiediary.models.data.repository.ItemRepository
import kotlinx.coroutines.launch

class FormViewModel(context : Context): ViewModel() {

    private val itemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())

    fun addItem(
        ean : Long,
        name : String,
        energy : Double,
        fat : Double,
        carbohydrates : Double,
        sugar : Double,
        fiber : Double,
        protein : Double,
        salt : Double)
    {
        viewModelScope.launch {
            val newItem = Item(
                ean,
                name,
                energy,
                fat,
                carbohydrates,
                sugar,
                fiber,
                protein,
                salt
            )
            itemRepository.insert(newItem)
        }
    }
}