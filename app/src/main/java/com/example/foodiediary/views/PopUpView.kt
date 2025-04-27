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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.foodiediary.models.data.entity.Added
import com.example.foodiediary.viewmodels.DatabaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import com.example.foodiediary.viewmodels.DatabaseViewModel
import com.example.foodiediary.viewmodels.PopUpViewModel
import androidx.compose.ui.platform.LocalContext

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun PopUpView(
    showPopup: Boolean,
    closePopup: () -> Unit,
    barcode: Long,
    navController: NavController
) {
    val context = LocalContext.current
    val db = DatabaseViewModel(context)
    val viewModel = PopUpViewModel(db)
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
                            .size(250.dp)
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
                                    - ${item.protein}g 
                                    - ${item.fat}g 
                                    - ${item.carbohydrates}g 
                                    - ${item.energy}kcal
                                    """.trimIndent(),
                                modifier = Modifier.padding(4.dp)
                            )
                        } else {
                            Text("No item found")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            val timestamp = System.currentTimeMillis()
                            val eanLong = ean?.toLongOrNull()
                            CoroutineScope(Dispatchers.IO).launch {
                                if (dbViewModel.getAddedByTimeStamp(timestamp) == null && eanLong != null) {
                                    dbViewModel.insertAdded(
                                        Added(
                                            ean = eanLong
                                        )
                                    )
                                } else {
                                    // Handle the case where the item is already in the diary
                                    closePopup()
                                }
                            }

                        }) {
                            Text("Add to Diary")
                        }
                        Button(onClick = {
                            isVisible = false
                            navController.navigate("homeView")
                        }) {
                            Text("Close")
                        }
                    }
                }
            }
        }
    }
}
