package com.example.foodiediary.views

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodiediary.models.data.entity.Added
import com.example.foodiediary.models.data.entity.Favorite
import com.example.foodiediary.utils.PopUpViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.foodiediary.viewmodels.PopUpViewModel

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
    val item by viewModel.barcodeItem.collectAsState()
    var isVisible by remember { mutableStateOf(showPopup) }
    val alpha: Float by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "alpha"
    )

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
                            .size(width = 250.dp, height = 450.dp)
                            .scale(scale)
                            .alpha(alpha)
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (item.ean != 0L) {
                            Text(
                                text = """
                                    ${item.ean} 
                                    - ${item.name} 
                                    - ${item.energy} kcal 
                                    - ${item.fat} g 
                                    - ${item.carbohydrates} g 
                                    - ${item.sugar} g 
                                    - ${item.fiber} g
                                    - ${item.protein} g 
                                    - ${item.salt} g
                                    """.trimIndent(),
                                modifier = Modifier.padding(4.dp)
                            )
                        } else {
                            Text("No item found")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            val eanLong = ean?.toLongOrNull()
                            if (eanLong != null) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.addItemToDiary(Added(ean = eanLong))
                                }
                            }

                        }) {
                            Text("Add to Diary")
                        }
                        Button(onClick = {
                            val eanLong = ean?.toLongOrNull()
                            if (eanLong != null) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.addItemToFavorites(Favorite(ean = eanLong))
                                }
                            }

                        }) {
                            Text("Add to favorites")
                        }
                        Button(onClick = {
                            val eanLong = ean?.toLongOrNull()
                            if (eanLong != null) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.deleteItemFromFavorites(viewModel.favoriteRepository.getFavoriteByEan(eanLong))
                                }
                            }

                        }) {
                            Text("Delete from favorites")
                        }
                        Button(onClick = {
                            isVisible = false
                        }) {
                            Text("Close")
                        }
                    }
                }
            }
        }
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
