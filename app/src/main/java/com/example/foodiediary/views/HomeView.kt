package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import com.example.foodiediary.models.data.database.AppDatabase
import com.example.foodiediary.models.data.repository.ItemRepository
import com.example.foodiediary.ui.theme.AppleRed
import com.example.foodiediary.ui.theme.FoodieDiaryTheme
import com.example.foodiediary.ui.theme.GrassGreen
import com.example.foodiediary.ui.theme.LightGreen
import com.example.foodiediary.ui.theme.PureWhite
import com.example.foodiediary.utils.HomeViewModelFactory
import com.example.foodiediary.viewmodels.HomeViewModel

@Composable
fun HomeView(
    navController: NavController
) {
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(LocalContext.current)
    )
    val allItems = viewModel.allItems.collectAsState(initial = emptyList())
    val allFavoriteItems = viewModel.allFavoriteItems.collectAsState(initial = emptyList())


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppleRed,
                        LightGreen,
                        GrassGreen
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text("Home Screen Content")
        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
        item {
            Column(
                modifier = Modifier
                    .width(500.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .background(PureWhite)
                        .padding(20.dp)
                        .width(250.dp)
                        .height(100.dp)
                )
                {
                    Text("Proteins, Fats, Carbs, Calories")
                }
                Spacer(modifier = Modifier.height(30.dp))
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .background(PureWhite)
                        .padding(20.dp)
                        .width(250.dp)
                        .height(100.dp)
                ) {
                    Text("Water intake")
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = { navController.navigate("cameraView") }) {
                    Text("Scan Barcode (EAN)")
                }
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .background(PureWhite)
                        .padding(20.dp)
                        .width(250.dp)
                        .height(400.dp)
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Text("All Items")
                        }
                        items(allItems.value) { item ->
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
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .background(PureWhite)
                        .padding(20.dp)
                        .width(250.dp)
                        .height(400.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Text("Favorite Items")
                        }
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
            }
        }
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
