package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.entity.Item
import com.example.foodiediary.models.data.repository.ItemRepository
import kotlinx.coroutines.launch

/**
 * FormViewModel is a ViewModel class that manages the data and operations for the form screen.
 * It interacts with the database to perform CRUD operations on items.
 *
 * @param context The context used to access the database.
 *
 * This ViewModel uses the ItemRepository to interact with the database.
 * It provides a method to add a new item to the database.
 *
 * This ViewModel is used in the Form screen to handle user input and save new items.
 */
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