package com.example.foodiediary.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodiediary.utils.SearchViewModelFactory
import com.example.foodiediary.viewmodels.SearchViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(onSearch: (String) -> Unit, navController: NavController) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(LocalContext.current)
    )
    val searchResults by viewModel.searchResults.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f }
                .fillMaxWidth(),
            query = searchText,
            onQueryChange = { newText ->
                searchText = newText
                viewModel.searchItems(newText)
            },
            onSearch = { query ->
                onSearch(query)
                active = false
                keyboardController?.hide() // Hide the keyboard
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("Search") },
            leadingIcon = { } // Can add a search icon here if you like
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(searchResults) { item ->
                    ListItem(
                        headlineContent = { Text(item.name) },
                        modifier = Modifier.clickable {
                            navController.navigate("popupView/${item.ean}")
                            active = false
                            keyboardController?.hide()
                        }
                    )
                }
            }
        }
    }
}