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

/**
 * PopUpViewModel is a ViewModel class that manages the data and operations for the pop-up view.
 * It interacts with the database to perform CRUD operations on items, added items, and favorites.
 *
 * @param context The context used to access the database.
 *
 * This ViewModel uses the ItemRepository, AddedRepository, and FavoriteRepository to interact with the database.
 * It exposes a StateFlow for the barcode item and a MutableStateFlow for the favorite button text.
 *
 * It provides methods to update the favorite button text, get barcode data, add items to the diary and favorites,
 * delete items from favorites and diary, and delete items from the database.
 */
class PopUpViewModel(context: Context) : ViewModel() {

    val appDB = AppDatabase.getInstance(context)

    internal val itemRepository = ItemRepository(appDB.itemDao())
    internal val addedRepository = AddedRepository(appDB.addedDao())
    internal val favoriteRepository = FavoriteRepository(appDB.favoriteDao())

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

    fun deleteItemFromDiary(added: Added?) {
        if (added != null) {
            viewModelScope.launch {
                addedRepository.delete(added)
            }
        }
    }

    fun deleteItem(itemByEan: Item?) {
        if (itemByEan != null) {
            viewModelScope.launch {
                itemRepository.delete(itemByEan)
            }
        }
    }

    fun deleteFromEverywhere(item: Item?) {
        if (item != null) {
            viewModelScope.launch {
                val added = addedRepository.getAddedByEan(item.ean)
                val favorite = favoriteRepository.getFavoriteByEan(item.ean)
                if (added != null) {
                    addedRepository.deleteByEan(item.ean)
                }
                if (favorite != null) {
                    favoriteRepository.delete(favorite)
                }
                itemRepository.delete(item)
            }
        }
    }

    fun deleteByTimeStamp(timeStamp: Long) {
        viewModelScope.launch {
            addedRepository.deleteByTimeStamp(timeStamp)
        }
    }
}