package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.entity.Item
import com.example.foodiediary.models.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.text.contains

class SearchViewModel(context: Context) : ViewModel() {
    private val itemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())

    private val _searchResults = MutableStateFlow<List<Item>>(emptyList())
    val searchResults: StateFlow<List<Item>> = _searchResults.asStateFlow()
    private var allItems : List<Item> = emptyList()
    private val maxResults = 5 // Limit the number of results

    init {
        getAllItems()
    }

    fun searchItems(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            val filteredList = allItems.filter { it.name.contains(query, ignoreCase = true) }
            _searchResults.value = filteredList.take(maxResults)
        }
    }

    private fun getAllItems() {
        viewModelScope.launch {
            itemRepository.getAllItems().collectLatest { list ->
                allItems = list
                searchItems("")
            }
        }
    }
}
