package com.example.foodiediary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.repository.AddedRepository
import com.example.foodiediary.models.data.repository.FavoriteRepository
import com.example.foodiediary.models.data.repository.ItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class HomeViewModel(context: Context) : ViewModel() {

    private val itemRepository = ItemRepository(AppDatabase.getInstance(context).itemDao())
    private val addedRepository = AddedRepository(AppDatabase.getInstance(context).addedDao())

    val allItems = itemRepository.getAllItems()
    private val favoriteRepository = FavoriteRepository(AppDatabase.getInstance(context).favoriteDao())
    private val allFavorites = favoriteRepository.getAllFavorites

    @OptIn(ExperimentalCoroutinesApi::class)
    val allFavoriteItems = allFavorites.flatMapLatest { favorites ->
        allItems.map { items ->
            items.filter { item ->
                favorites.any { favorite -> favorite.ean == item.ean }
            }
        }
    }

    fun getDailyNutrientTotals(): Flow<Map<String, Double>> {

        val startTime = LocalDate.now()
            .atStartOfDay()
            .toInstant(java.time.ZoneOffset.UTC)
            .toEpochMilli()

        val endTime = LocalDate.now()
            .plusDays(1)
            .atStartOfDay()
            .toInstant(java.time.ZoneOffset.UTC)
            .toEpochMilli()

        return addedRepository.getItemsInTimeStampRange(startTime, endTime).map { addedList ->
            val totals = mutableMapOf(
                "energy" to 0.0,
                "fat" to 0.0,
                "carbohydrates" to 0.0,
                "sugar" to 0.0,
                "fiber" to 0.0,
                "protein" to 0.0,
                "salt" to 0.0
            )

            // Calculate totals
            addedList.forEach { added ->
                val item = itemRepository.getItemByEan(added.ean)
                if (item != null) {
                    totals["energy"] = totals.getValue("energy") + item.energy
                    totals["fat"] = totals.getValue("fat") + item.fat
                    totals["carbohydrates"] = totals.getValue("carbohydrates") + item.carbohydrates
                    totals["sugar"] = totals.getValue("sugar") + item.sugar
                    totals["fiber"] = totals.getValue("fiber") + item.fiber
                    totals["protein"] = totals.getValue("protein") + item.protein
                    totals["salt"] = totals.getValue("salt") + item.salt
                }
            }
            totals
        }
    }
}

