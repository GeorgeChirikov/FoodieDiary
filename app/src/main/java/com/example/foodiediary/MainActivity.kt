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
import com.example.foodiediary.views.ScreenWithDrawer
import com.example.foodiediary.views.HomeViewmodel
import com.example.foodiediary.views.SearchViewmodel
import com.example.foodiediary.views.FavoritesViewmodel
import com.example.foodiediary.views.LoginViewmodel
import com.example.foodiediary.views.SettingsViewmodel
import com.example.foodiediary.views.SignupView

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
        ?: "homeView" // Default route if null

    NavHost(navController = navController, startDestination = "homeView") {
        composable("homeView") {
            ScreenWithDrawer(navController, currentRoute) {
                HomeViewmodel(navController)
            }
        }
        composable("cameraView") {
            ScreenWithDrawer(navController, currentRoute) {
                Prelude()
            }
        }
        composable("searchView") {
            ScreenWithDrawer(navController, currentRoute) {
                SearchViewmodel()
            }
        }
        composable("favoritesView") {
            ScreenWithDrawer(navController, currentRoute) {
                FavoritesViewmodel()
            }
        }
        composable("settingsView") {
            ScreenWithDrawer(navController, currentRoute) {
                SettingsViewmodel()
            }
        }
        composable("loginView") {
            ScreenWithDrawer(navController, currentRoute) {
                LoginViewmodel(navController)
            }
        }
        composable("signupView") {
            ScreenWithDrawer(navController, currentRoute) {
                SignupView(navController)
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