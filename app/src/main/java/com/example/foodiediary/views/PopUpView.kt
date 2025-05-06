package com.example.foodiediary.views

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodiediary.models.data.entity.Added
import com.example.foodiediary.models.data.entity.Favorite
import com.example.foodiediary.utils.PopUpViewModelFactory
import com.example.foodiediary.viewmodels.PopUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun PopUpView(
    ean: String?,
    showPopup: Boolean,
    closePopup: () -> Unit,
) {
    val barcode = ean?.toLongOrNull() ?: 0L
    val viewModel: PopUpViewModel = viewModel(
        factory = PopUpViewModelFactory(LocalContext.current)
    )
    viewModel.getBarcodeData(barcode)
    viewModel.updateFavoriteButtonText(barcode)
    val item by viewModel.barcodeItem.collectAsState()
    var isVisible by remember { mutableStateOf(showPopup) }
    val alpha: Float by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "alpha"
    )

    val favoriteButtonText by viewModel.favoriteButtonText.collectAsState()

    //Add to diary msg state
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    // Add to diary msg key, used to trigger the snackbar
    var snackbarKey by remember { mutableIntStateOf(0) }

    val scale: Float by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = tween(durationMillis = 300),
        label = "scale"
    )

    if (showPopup) {
        if (alpha == 0f) {
            LaunchedEffect(Unit) {
                closePopup()
            }
        } else {
            Dialog(
                onDismissRequest = {
                    isVisible = false
                    closePopup()
                },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    dismissOnClickOutside = true
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .size(width = 250.dp, height = 420.dp)
                            .scale(scale)
                            .alpha(alpha)
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = {
                                isVisible = false
                                snackbarKey = 0
                            }) {
                                Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        if (item.ean != 0L) {
                            Column(
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(text = item.ean.toString())
                                Text(text = item.name)
                                Spacer(modifier = Modifier.height(8.dp))

                                NutrientRow(label = "Energy", value = "${item.energy} kcal")
                                NutrientRow(label = "Fat", value = "${item.fat} g")
                                NutrientRow(
                                    label = "Carbohydrates",
                                    value = "${item.carbohydrates} g"
                                )
                                NutrientRow(label = "Sugar", value = "${item.sugar} g")
                                NutrientRow(label = "Fiber", value = "${item.fiber} g")
                                NutrientRow(label = "Protein", value = "${item.protein} g")
                                NutrientRow(label = "Salt", value = "${item.salt} g")
                            }
                        } else {
                            Text("No item found")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            val eanLong = ean?.toLongOrNull()
                            if (eanLong != null) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.addItemToDiary(Added(ean = eanLong))
                                snackbarKey++
                                }
                            }

                        }) {
                            Text("Add to Diary")
                        }
                        Button(onClick = {
                            val eanLong = ean?.toLongOrNull()
                            if (eanLong != null) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    if (favoriteButtonText == "Add to favorites") {
                                        viewModel.addItemToFavorites(Favorite(ean = eanLong))
                                    } else {
                                        viewModel.deleteItemFromFavorites(
                                            viewModel.favoriteRepository.getFavoriteByEan(
                                                eanLong
                                            )
                                        )
                                    }

                                }
                            }

                        }) {
                            Text(favoriteButtonText)
                        }
                    }
                    // Shows msg when item is added to diary
                    SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(8.dp))
                }
            }
            // Trigger add to diary msg if key is bigger than 0
            if (snackbarKey>0) {
                LaunchedEffect(snackbarKey) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Item added to diary!")
                    }
                }
            }
        }
    }
}

@Composable
fun NutrientRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}

@Preview
@Composable
fun PopUpViewPreview() {
    PopUpView(
        ean = "1234567890123",
        showPopup = true,
        closePopup = {}
    )
}
