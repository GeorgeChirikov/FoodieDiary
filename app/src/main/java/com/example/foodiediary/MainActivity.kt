package com.example.foodiediary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.ui.theme.FoodieDiaryTheme
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import com.example.foodiediary.ui.theme.AppleRed
import com.example.foodiediary.ui.theme.GrassGreen
import com.example.foodiediary.ui.theme.LightGreen
import com.example.foodiediary.ui.theme.PureWhite
import com.example.foodiediary.ui.theme.ShyGreen

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
                HomeScreenContent()
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

//Drawer composable
@Composable
fun ScreenWithDrawer(
    navController: NavController,
    currentRoute: String,
    content: @Composable () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier
            .background(AppleRed),
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController,
                currentRoute = currentRoute,
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        },
        content = {
            Column {
                TopAppBar(
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
                content() // Screen's content will be placed
            }
        }
    )
}

@Composable
fun TopAppBar(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(Icons.Filled.Menu, contentDescription = "Menu")
        }
        Spacer(modifier = Modifier.width(20.dp))
        // Other elements can be added
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    currentRoute: String,
    onCloseDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .requiredWidth(140.dp)
            .background(ShyGreen)
            .padding(16.dp)
            .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
    ) {
        // Navigation items
        DrawerItem("Home", "home", currentRoute, navController, onCloseDrawer)
        DrawerItem("Page 1", "page1", currentRoute, navController, onCloseDrawer)
        DrawerItem("Page 2", "page2", currentRoute, navController, onCloseDrawer)
        DrawerItem("Page 3", "page3", currentRoute, navController, onCloseDrawer)
    }
}

@Composable
fun DrawerItem(
    label: String,
    route: String,
    currentRoute: String,
    navController: NavController,
    onCloseDrawer: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label) },
        selected = currentRoute == route,
        onClick = {
            onCloseDrawer()
            if (currentRoute != route) { // Only navigate if not already on the destination
                navController.navigate(route)
            }
        }
    )
}

@Composable
fun HomeScreenContent() {
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
                Box(modifier = Modifier
                    .background(PureWhite)
                    .padding(20.dp)
                    .width(200.dp)
                    .height(30.dp)
                ) {
                    Text("Scan items button")
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


@Composable
fun Page1ScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(LightGreen, ShyGreen)))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is Page 1")
    }
}

@Composable
fun Page2ScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(AppleRed, LightGreen, GrassGreen)))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is Page 2")
    }
}

@Composable
fun Page3ScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(AppleRed, LightGreen, GrassGreen)))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is Page 3")
    }
}

// Create more Composable functions for additional pages


@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    FoodieDiaryTheme {
        AppNavigation()
    }
}