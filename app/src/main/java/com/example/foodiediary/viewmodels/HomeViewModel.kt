package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.repository.ItemRepository

class HomeViewModel(context: Context) : ViewModel() {
    val itemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())
    val allItems = itemRepository.getAllItems()
}