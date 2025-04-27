package com.example.foodiediary.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodiediary.models.data.entity.Favorite
import com.example.foodiediary.models.data.entity.Item
import com.example.foodiediary.ui.theme.AppleRed
import com.example.foodiediary.ui.theme.GrassGreen
import com.example.foodiediary.ui.theme.LightGreen

@Composable
fun FavoritesView(navController: NavController) {
    val context = LocalContext.current
    val db = DatabaseViewModel(context)
    val allFavorites = db.favorites.collectAsState(initial = listOf<Item>())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(AppleRed, LightGreen, GrassGreen)))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(allFavorites.value) { item ->
            Text(
                text = """
                    ${item.ean} 
                    - ${item.name} 
                    - ${item.protein}g 
                    - ${item.fat}g 
                    - ${item.carbohydrates}g 
                    - ${item.energy}kcal
                    """.trimIndent(),
                modifier = Modifier.padding(4.dp)
                    .clickable{
                        Toast.makeText(context, "Clicked on ${item.name}", Toast.LENGTH_SHORT).show()
                        navController.navigate("popupView/${item.ean}")
                    }
            )
        }
    }
}