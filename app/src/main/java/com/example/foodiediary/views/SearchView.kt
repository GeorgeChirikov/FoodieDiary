package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodiediary.utils.SearchViewModelFactory
import com.example.foodiediary.viewmodels.SearchViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.ui.theme.FoodieDiaryTheme
import com.example.foodiediary.ui.theme.GradientBackground

/**
 * SearchView is a composable function that displays a search bar and a list of search results.
 * It allows users to search for items and navigate to a detailed view of the selected item.
 *
 * @Composable
 * SearchView: The main composable function for the search view.
 *
 * @param onSearch: A lambda function that is called when a search is performed.
 * @param navController: The NavController used for navigation between screens.
 *
 * This function uses the SearchViewModel to manage the search results and handle the search logic.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    onSearch: (String) -> Unit,
    navController: NavController)
{

    var searchText by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(LocalContext.current)
    )

    val searchResults by viewModel.searchResults.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { isTraversalGroup = true }
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        active = false
                    })
                }
        ) {

            SearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .semantics { traversalIndex = 0f }
                    .fillMaxWidth()
                    .padding(16.dp),
                query = searchText,
                onQueryChange = { newText ->
                    searchText = newText
                    viewModel.searchItems(newText)
                },
                onSearch = { query ->
                    onSearch(query)
                    active = false
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
                active = active,
                onActiveChange = {
                    active = it
                    if (!active) {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                },
                placeholder = { Text("Search") },
                leadingIcon = { } // Can add a search icon here if you like
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                ) {

                    items(searchResults) { item ->
                        ListItem(
                            headlineContent = { Text(item.name) },
                            modifier = Modifier.clickable {
                                navController.navigate("popupView/${item.ean}")
                                active = false
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchViewPreview() {
    val navController = rememberNavController()
    FoodieDiaryTheme {
        SearchView(
            onSearch = {},
            navController = navController
        )
    }
}