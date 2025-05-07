package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.ui.theme.FoodieDiaryTheme
import com.example.foodiediary.ui.theme.GradientBackground
import com.example.foodiediary.utils.FavoritesViewModelFactory
import com.example.foodiediary.viewmodels.FavoritesViewModel

@Composable
fun FavoritesView(navController: NavController) {

    val viewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(LocalContext.current)
    )

    val allFavoriteItems = viewModel.allFavoriteItems.collectAsState(initial = emptyList())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Header
        Text(
            text = "Favorites",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 36.dp)
                .padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier) {
            items(allFavoriteItems.value) { item ->
                Text(
                    text = """
                        ${item.ean} 
                        ${item.name} 
                        - protein: ${item.protein}g 
                        - fat: ${item.fat}g 
                        - carbs: ${item.carbohydrates}g 
                        - energy: ${item.energy}kcal
                        """.trimIndent(),
                    modifier = Modifier.padding(4.dp)
                        .clickable {
                            navController.navigate("popupView/${item.ean}")
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesViewPreview() {
    val navController = rememberNavController()
    FoodieDiaryTheme {
        FavoritesView(navController = navController)
    }
}