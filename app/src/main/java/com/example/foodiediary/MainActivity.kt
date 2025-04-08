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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
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
import com.example.foodiediary.ui.theme.PureBlack
import com.example.foodiediary.ui.theme.PurpleGrey40
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import com.example.foodiediary.ui.theme.AppleRed
import com.example.foodiediary.ui.theme.GrassGreen
import com.example.foodiediary.ui.theme.LightGreen
import com.example.foodiediary.ui.theme.PureWhite

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
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("page1") { Page1Screen() }
        composable("page2") { Page2Screen() }
        // Add more pages here as needed
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier
            .background(brush = Brush.verticalGradient(colors = listOf(AppleRed, LightGreen, GrassGreen))),
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier
                .requiredWidth(140.dp)
                .background(PurpleGrey40)
                .padding(16.dp) ) {
                NavigationDrawerItem(
                    label = { Text("Page 1", modifier = Modifier.background(PureBlack)) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("page1")
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Page 2") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("page2")
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Close Drawer") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("home")
                        }
                    }
                )
                // Add more items as needed
            }
        },
        content = {
            Column() {
                Row() {
                    Column {
                        IconButton(modifier = Modifier.padding(15.dp), onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Column(modifier = Modifier
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                    ) {
                        Text("Home Screen")
                    }
                }
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
    )
}

@Composable
fun Page1Screen() {
    Text("This is Page 1")
}

@Composable
fun Page2Screen() {
    Text("This is Page 2")
}
// Create more Composable functions for additional pages


@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    FoodieDiaryTheme {
        AppNavigation()

    }
}