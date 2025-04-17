package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodiediary.ui.theme.AppleRed
import com.example.foodiediary.ui.theme.GrassGreen
import com.example.foodiediary.ui.theme.LightGreen
import com.example.foodiediary.ui.theme.PureWhite

@Composable
fun HomeView(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(AppleRed, LightGreen, GrassGreen)))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Screen Content")
        Spacer(modifier = Modifier.height(30.dp))
        Column(modifier = Modifier
            .width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier
                .background(PureWhite)
                .padding(20.dp)
                .width(200.dp)
                .height(100.dp)
            )
            {
                Text("Proteins, Fats, Carbs, Calories")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(modifier = Modifier
                .background(PureWhite)
                .padding(20.dp)
                .width(200.dp)
                .height(100.dp)
            ) {
                Text("Water intake")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = { navController.navigate("cameraView") }) {
                Text("Scan Barcode (EAN)")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(modifier = Modifier
                .background(PureWhite)
                .padding(20.dp)
                .width(200.dp)
                .height(30.dp)
            ) {
                Text("History")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(modifier = Modifier
                .background(PureWhite)
                .padding(20.dp)
                .width(200.dp)
                .height(30.dp)
            ) {
                Text("Favorites")
            }
        }

    }
}
