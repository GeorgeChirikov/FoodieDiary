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

/**
 * SearchViewModel is a ViewModel class that manages the search functionality for items.
 * It retrieves all items from the database and filters them based on the search query.
 *
 * @param context The context used to access the database.
 *
 * This ViewModel uses the ItemRepository to interact with the database and fetch all items.
 *
 * It exposes a StateFlow of search results that can be observed by the UI.
 */
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
