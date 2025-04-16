package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiediary.ui.theme.LightGreen
import com.example.foodiediary.ui.theme.NatureWhite
import com.example.foodiediary.ui.theme.YellowStone

@Composable
fun SignupView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        YellowStone,
                        LightGreen
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Column(modifier = Modifier.background(NatureWhite).padding(35.dp)) {
            Text(modifier = Modifier.padding(20.dp), fontSize = 35.sp, text = "Sign up")
            Spacer(modifier = Modifier.size(20.dp))
            Text("Username")
            TextField(value = "", onValueChange = {})
            Spacer(modifier = Modifier.size(20.dp))
            Text("Email")
            TextField(value = "", onValueChange = {})
            Spacer(modifier = Modifier.size(20.dp))
            Text("Password")
            TextField(value = "", onValueChange = {})
            Spacer(modifier = Modifier.size(20.dp))
            Button(onClick = { navController.navigate("homeView") }) { Text("Sign up") }
            Button(onClick = { navController.navigate("loginView") }) { Text("Already have an account? Login instead") }
        }
    }
}