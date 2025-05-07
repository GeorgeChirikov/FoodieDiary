package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.utils.HomeViewModelFactory
import com.example.foodiediary.viewmodels.HomeViewModel
import com.example.foodiediary.ui.theme.FoodieDiaryTheme
import com.example.foodiediary.ui.theme.GradientBackground
import kotlin.math.roundToLong

@Composable
fun HomeView(navController: NavController) {

    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(LocalContext.current)
    )

    val allItems = viewModel.allItems.collectAsState(initial = emptyList())
    val allFavoriteItems = viewModel.allFavoriteItems.collectAsState(initial = emptyList())
    val nutrientTotals: Map<String, Double> by viewModel.getDailyNutrientTotals().collectAsState(initial = emptyMap())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Header
        item {
            Text(
                text = "Home",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(bottom = 16.dp)
            )
        }

        // Column for cards
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Card for macronutrients
                CustomCard(
                    modifier = Modifier
                        .wrapContentHeight()
                    ) {

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Daily Totals:")
                        Spacer(modifier = Modifier.height(8.dp))

                        NutrientRow(label = "Energy", value = "${String.format("%.1f", nutrientTotals["energy"] ?: 0.0)} kcal")
                        NutrientRow(label = "Fat", value = "${String.format("%.1f", nutrientTotals["fat"] ?: 0.0)} g")
                        NutrientRow(label = "Carbohydrates", value = "${String.format("%.1f", nutrientTotals["carbohydrates"] ?: 0.0)} g")
                        NutrientRow(label = "Sugar", value = "${String.format("%.1f", nutrientTotals["sugar"] ?: 0.0)} g")
                        NutrientRow(label = "Fiber", value = "${String.format("%.1f", nutrientTotals["fiber"] ?: 0.0)} g")
                        NutrientRow(label = "Protein", value = "${String.format("%.1f", nutrientTotals["protein"] ?: 0.0)} g")
                        NutrientRow(label = "Salt", value = "${String.format("%.1f", nutrientTotals["salt"] ?: 0.0)} g")
                    }
                }

                // Card for water intake
                CustomCard(
                    modifier = Modifier.height(150.dp)
                )  {
                    Text(
                        text = "Water intake",
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }

                // Button to scan a barcode
                Button(
                    onClick = { navController.navigate("cameraView") },
                    modifier = Modifier
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary)
                ){
                    Text("Scan Barcode (EAN)")
                }

                // Card for all items
                Card(
                    modifier = Modifier
                        .padding(24.dp)
                        .height(400.dp)
                        .shadow(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface),
                    shape = MaterialTheme.shapes.medium
                ) {

                    Text(
                        text = "All Items",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .padding(top = 16.dp),
                        textAlign = TextAlign.Center,
                        )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        items(allItems.value) { item ->
                            Text(
                                text = """
                                ${item.name} 
                                - protein: ${item.protein}g 
                                - fat: ${item.fat}g 
                                - carbs: ${item.carbohydrates}g 
                                - energy: ${item.energy}kcal
                                """.trimIndent(),
                                modifier = Modifier
                                    .padding(4.dp)
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
                
                // Card for favorite items
                Card(
                    modifier = Modifier
                        .padding(24.dp)
                        .height(400.dp)
                        .shadow(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface),
                    shape = MaterialTheme.shapes.medium
                ) {

                    Text(
                        text = "Favorite Items",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .padding(top = 16.dp),
                        textAlign = TextAlign.Center,
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(allFavoriteItems.value) { item ->
                            Text(
                                text = """
                                ${item.name} 
                                - protein: ${item.protein}g 
                                - fat: ${item.fat}g 
                                - carbs: ${item.carbohydrates}g 
                                - energy: ${item.energy}kcal
                                """.trimIndent(),
                                modifier = Modifier
                                    .padding(4.dp)
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
        }
    }
}

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface),
    content: @Composable () -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
            .shadow(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = colors
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    val navController = rememberNavController()
    FoodieDiaryTheme {
        HomeView(navController = navController)
    }
}
