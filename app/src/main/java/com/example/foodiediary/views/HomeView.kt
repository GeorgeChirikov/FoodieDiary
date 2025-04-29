package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.AppNavigation
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

    // specified modifier to avoid repetition and improve consistency
    val spacerModifier = Modifier
        .height(16.dp)


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
            Text(
                text = "Home Screen Content",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(bottom = 16.dp)
            )
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CustomCard() {
                    Text(
                        text = "Proteins, Fats, Carbs, Calories",
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
                CustomCard() {
                    Text(
                        text = "Water intake",
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
                Spacer(modifier = spacerModifier)
                Button(onClick = { navController.navigate("cameraView") }) {
                    Text("Scan Barcode (EAN)")
                }
                Spacer(modifier = spacerModifier)
                CustomCard() {
                    Text(
                        text = "lorem ipsum",
                        modifier = Modifier
                            .padding(16.dp)
                    )
                } {
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
                        .height(30.dp)
                ) {
                    Text("Favorites")
                }
            }
        }
    }
}

@Composable
fun CustomCard(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        // .wrapContentHeight()
        .height(100.dp)
        .padding(24.dp)
        .clip(MaterialTheme.shapes.medium)
        .shadow(8.dp),

    colors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
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
