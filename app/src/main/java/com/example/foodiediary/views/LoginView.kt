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
import com.example.foodiediary.ui.theme.YellowStone

@Composable
fun LoginViewmodel(navController: NavController) {
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
        Text(modifier = Modifier.padding(20.dp), fontSize = 35.sp, text = "Login")
        Spacer(modifier = Modifier.size(20.dp))
        Text("Username or Email")
        TextField(value = "", onValueChange = {})
        Spacer(modifier = Modifier.size(20.dp))
        Text("Password")
        TextField(value = "", onValueChange = {})
        Spacer(modifier = Modifier.size(20.dp))
        Button(onClick = { navController.navigate("homeView") }) { Text("Login") }
        Button(onClick = { navController.navigate("signupView") }) { Text("Don't have an account? Sign up instead") }
        }
    }
