package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.ui.theme.FoodieDiaryTheme
import com.example.foodiediary.ui.theme.GradientBackground
import com.example.foodiediary.utils.FormViewModelFactory
import com.example.foodiediary.viewmodels.FormViewModel

@Composable
fun FormView(
    ean: String,
    navController: NavController
) {
    var item by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbohydrates by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var energy by remember { mutableStateOf("") }
    var sugar by remember { mutableStateOf("") }
    var fiber by remember { mutableStateOf("") }
    var salt by remember { mutableStateOf("") }

    val viewModel: FormViewModel = viewModel(
        factory = FormViewModelFactory(LocalContext.current)
    )
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val fields = listOf(
        "item" to item,
        "protein" to protein,
        "fat(g)" to fat,
        "carbohydrates" to carbohydrates,
        "energy(kcal)" to energy,
        "sugar" to sugar,
        "fiber" to fiber,
        "salt" to salt
    )

    Column(
        modifier = Modifier
            .background(GradientBackground)
            .fillMaxSize()
            .then(Modifier.background(Color.Black.copy(alpha = 0.4f)))
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
                    .shadow(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Fill out information",
                        fontSize = 22.sp,
                    )
                    HorizontalDivider(
                        thickness = 2.dp,
                        modifier = Modifier
                            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "EAN: ${ean}", // item.ean
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.height(16.dp))


                    fields.forEach { (label, value) ->
                        TextField(
                            value = when (label) {
                                "item" -> item
                                "protein" -> protein
                                "fat(g)" -> fat
                                "carbohydrates" -> carbohydrates
                                "energy(kcal)" -> energy
                                "sugar" -> sugar
                                "fiber" -> fiber
                                "salt" -> salt
                                else -> ""
                            },
                            onValueChange = { newValue ->
                                when (label) {
                                    "item" -> item = newValue
                                    "protein" -> protein = newValue
                                    "fat(g)" -> fat = newValue
                                    "carbohydrates" -> carbohydrates = newValue
                                    "energy(kcal)" -> energy = newValue
                                    "sugar" -> sugar = newValue
                                    "fiber" -> fiber = newValue
                                    "salt" -> salt = newValue
                                }
                            },
                            label = { Text(label) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        // Cancel button
                        Button(
                            onClick = { navController.popBackStack() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError
                            ),
                            modifier = Modifier
                                .width(140.dp)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Cancel"
                            )
                        }
                        // Add button
                        Button(
                            onClick = {

                                viewModel.addItem(
                                    ean = ean.toLong(),
                                    name = item,
                                    energy = energy.toDouble(),
                                    fat = fat.toDouble(),
                                    carbohydrates = carbohydrates.toDouble(),
                                    sugar = sugar.toDouble(),
                                    fiber = fiber.toDouble(),
                                    protein = protein.toDouble(),
                                    salt = salt.toDouble()
                                )
                                navController.popBackStack()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier
                                .width(140.dp)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Add item"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewForm () {
    val navController = rememberNavController()
    FoodieDiaryTheme {
        FormView(
            ean = "64787687",
            navController = navController
        )
    }
}
