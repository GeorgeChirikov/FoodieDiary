package com.example.foodiediary.views

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.ui.theme.AppleRed
import com.example.foodiediary.ui.theme.GradientBackground
import com.example.foodiediary.ui.theme.GrassGreen
import com.example.foodiediary.ui.theme.LightGreen
import com.example.foodiediary.utils.FavoritesViewModelFactory
import com.example.foodiediary.utils.PopUpViewModelFactory
import com.example.foodiediary.viewmodels.FavoritesViewModel

@Composable
fun FavoritesView(navController: NavController) {
    val viewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(LocalContext.current)
    )
    var allFavorites = viewModel.allFavorites.collectAsState(initial = emptyList())
    var allItems = viewModel.allItems.collectAsState(initial = emptyList())
    var allFavoriteItems = viewModel.allFavoriteItems.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(allFavoriteItems.value) { item ->
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
                        navController.navigate("popupView/${item.ean}")
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesViewPreview() {
    val navController = rememberNavController()
    FavoritesView(navController = navController)
}