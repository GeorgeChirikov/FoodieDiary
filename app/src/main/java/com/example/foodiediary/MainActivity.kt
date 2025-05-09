package com.example.foodiediary

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.ui.theme.FoodieDiaryTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.foodiediary.views.DiaryView
import com.example.foodiediary.views.Prelude
import com.example.foodiediary.views.ScreenWithDrawer
import com.example.foodiediary.views.HomeView
import com.example.foodiediary.views.SearchView
import com.example.foodiediary.views.FavoritesView
import com.example.foodiediary.views.FormView
import com.example.foodiediary.views.PopUpView


class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodieDiaryTheme {
                AppNavigation()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    var showPopup by remember { mutableStateOf(false) }
    var eanToSearch by remember { mutableStateOf<Long?>(null) }

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
                Prelude(
                    navController = navController,
                    showPopup = { barcode ->
                    showPopup = true
                    eanToSearch = barcode
                }
                )
            }
        }

        composable("searchView") {
            ScreenWithDrawer(navController, currentRoute) {
                SearchView(onSearch = { query -> navController.navigate("searchResultsView/$query") }, navController)
            }
        }
        composable("favoritesView") {
            ScreenWithDrawer(navController, currentRoute) {
                FavoritesView(navController)
            }
        }

        composable("diaryView") {
            ScreenWithDrawer(navController, currentRoute) {
                DiaryView(navController)
            }
        }

        composable("formView/{ean}") { backStackEntry ->
            val ean = backStackEntry.arguments?.getString("ean")
            if (ean != null) {
                FormView(
                    ean = ean,
                    navController = navController,
                )
            }
        }

        composable("popupView/{ean}") { backStackEntry ->
            val ean = backStackEntry.arguments?.getString("ean")
            PopUpView(ean, true, closePopup = { navController.popBackStack() })
        }
    }

    // Checks if popup is shown and if it should be closed
    if (showPopup && eanToSearch == null) {
        PopUpView(
            ean = null,
            showPopup = showPopup,
            closePopup = { showPopup = false })
    }
    
    if (showPopup && eanToSearch != null) {
        eanToSearch?.let {
            PopUpView(
                ean = it.toString(),
                showPopup = showPopup,
                closePopup = { showPopup = false })
        }
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    FoodieDiaryTheme {
        AppNavigation()
    }
}