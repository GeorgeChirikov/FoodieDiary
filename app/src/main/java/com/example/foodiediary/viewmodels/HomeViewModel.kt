package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodiediary.models.data.entity.Item
import kotlinx.coroutines.flow.Flow

class HomeViewModel(context: Context) : ViewModel() {
    val db = DatabaseViewModel(context)
    val allItems: Flow<List<Item>> = db.items
}