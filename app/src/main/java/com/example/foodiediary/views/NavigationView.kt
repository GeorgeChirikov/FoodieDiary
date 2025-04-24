package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodiediary.ui.theme.AppleRed
import com.example.foodiediary.ui.theme.ShyGreen
import kotlinx.coroutines.launch

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
            .fillMaxHeight()
    ) {
        // Navigation items
        DrawerItem("Home", "homeView", currentRoute, navController, onCloseDrawer)
        DrawerItem("Scan EAN", "cameraView", currentRoute, navController, onCloseDrawer)
        DrawerItem("Search", "searchView", currentRoute, navController, onCloseDrawer)
        DrawerItem("Favorites", "favoritesView", currentRoute, navController, onCloseDrawer)
        DrawerItem("History", "historyView", currentRoute, navController, onCloseDrawer)
        // DrawerItem("Settings", "settingsView", currentRoute, navController, onCloseDrawer)
        // DrawerItem("Logout", "loginView", currentRoute, navController, onCloseDrawer)
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