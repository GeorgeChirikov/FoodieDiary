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
import com.example.foodiediary.views.HomeView
import com.example.foodiediary.views.SearchView
import com.example.foodiediary.views.FavoritesView
import com.example.foodiediary.views.HistoryView


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
                HomeView(navController)
            }
        }
        composable("cameraView") {
            ScreenWithDrawer(navController, currentRoute) {
                Prelude()
            }
        }
        composable("searchView") {
            ScreenWithDrawer(navController, currentRoute) {
                SearchView( onSearch = { query -> navController.navigate("searchResultsView/$query") })
            }
        }
        composable("favoritesView") {
            ScreenWithDrawer(navController, currentRoute) {
                FavoritesView()
            }
        }
        composable("historyView") {
            ScreenWithDrawer(navController, currentRoute) {
                HistoryView(navController)
            }
        }
        /*
        composable("settingsView") {
            ScreenWithDrawer(navController, currentRoute) {
                SettingsView()
            }
        }
        composable("loginView") {
            ScreenWithDrawer(navController, currentRoute) {
                LoginView(navController)
            }
        }

        composable("signupView") {
            ScreenWithDrawer(navController, currentRoute) {
                SignupView(navController)
            }
        }
        */
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