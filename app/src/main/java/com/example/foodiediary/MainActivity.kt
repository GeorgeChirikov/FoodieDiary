package com.example.foodiediary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.ui.theme.FoodieDiaryTheme
import androidx.compose.runtime.collectAsState
import com.example.foodiediary.views.Prelude
import com.example.foodiediary.viewmodels.ScreenWithDrawer
import com.example.foodiediary.viewmodels.HomeScreenContent
import com.example.foodiediary.viewmodels.Page1ScreenContent
import com.example.foodiediary.viewmodels.Page2ScreenContent
import com.example.foodiediary.viewmodels.Page3ScreenContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryFlow
        .collectAsState(initial = navController.currentBackStackEntry)
        .value?.destination?.route
        ?: "home" // Default route if null

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            ScreenWithDrawer(navController, currentRoute) {
                HomeScreenContent(navController)
            }
        }
        composable("cameraView") {
            ScreenWithDrawer(navController, currentRoute) {
                Prelude()
            }
        }
        composable("page1") {
            ScreenWithDrawer(navController, currentRoute) {
                Page1ScreenContent()
            }
        }
        composable("page2") {
            ScreenWithDrawer(navController, currentRoute) {
                Page2ScreenContent()
            }
        }
        composable("page3") {
            ScreenWithDrawer(navController, currentRoute) {
                Page3ScreenContent()
            }
        }
        // ... other screens ...
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    FoodieDiaryTheme {
        AppNavigation()
    }
}