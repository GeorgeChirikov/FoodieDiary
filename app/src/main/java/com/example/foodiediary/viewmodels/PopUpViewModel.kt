package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.entity.Added
import com.example.foodiediary.models.data.entity.Favorite
import com.example.foodiediary.models.data.entity.Item
import com.example.foodiediary.models.data.repository.AddedRepository
import com.example.foodiediary.models.data.repository.FavoriteRepository
import com.example.foodiediary.models.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PopUpViewModel(context: Context) : ViewModel() {

    internal val itemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())
    private val addedRepository = AddedRepository(AppDatabase.getInstance(context).addedDao())
    internal val favoriteRepository = FavoriteRepository(AppDatabase.getInstance(context).favoriteDao())

    private val _barcodeItem = MutableStateFlow(Item(0,"",0.0,0.0,0.0,0.0,0.0,0.0,0.0))
    val barcodeItem = _barcodeItem.asStateFlow()

    val favoriteButtonText = MutableStateFlow("Add to favorites")

    fun updateFavoriteButtonText(ean: Long) {
        viewModelScope.launch {
            val favorite = favoriteRepository.getFavoriteByEan(ean)
            favoriteButtonText.value = if (favorite != null) "Remove from favorites" else "Add to favorites"
        }
    }

    fun getBarcodeData(barcode: Long) {
        viewModelScope.launch {
            val item = itemRepository.getItemByEan(barcode)
            _barcodeItem.value = item ?: Item(0,"",0.0,0.0,0.0,0.0,0.0,0.0,0.0)
        }
    }

    fun addItemToDiary(added: Added) {
        viewModelScope.launch {
            addedRepository.insert(added)
        }
    }

    fun addItemToFavorites(favorite: Favorite) {
        viewModelScope.launch {
            favoriteRepository.insert(favorite)
        }
        favoriteButtonText.value = "Remove from favorites"
    }

    fun deleteItemFromFavorites(favorite: Favorite?) {
        if (favorite != null) {
            viewModelScope.launch {
                favoriteRepository.delete(favorite)
            }
            favoriteButtonText.value = "Add to favorites"
        }
    }

    fun deleteItem(item: Item?) {
        if (item != null) {
            viewModelScope.launch {
                itemRepository.delete(item)
            }
        }
    }

}