package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.ui.theme.GradientBackground
import com.example.foodiediary.utils.FavoritesViewModelFactory
import com.example.foodiediary.viewmodels.FavoritesViewModel

@Composable
fun FavoritesView(navController: NavController) {

    val viewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(LocalContext.current)
    )

    val allFavoriteItems = viewModel.allFavoriteItems.collectAsState(initial = emptyList())

    Card(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .shadow(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
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
                    ${item?.name}
                    """.trimIndent(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = """
                    - ${item.energy}kcal
                    - ${item.fat}g 
                    - ${item.carbohydrates}g 
                    - ${item.sugar}g
                    - ${item.fiber}g
                    - ${item.protein}g 
                    - ${item.salt}g
                    - Ean: ${item.ean}
                    """.trimIndent(),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("popupView/${item.ean}")
                        }
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(start = 36.dp, end = 36.dp, top = 12.dp, bottom = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesViewPreview() {
    val navController = rememberNavController()
    FavoritesView(navController = navController)
}