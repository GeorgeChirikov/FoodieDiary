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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

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
    val scrollState = rememberScrollState()

    val viewModel: FormViewModel = viewModel(
        factory = FormViewModelFactory(LocalContext.current)
    )

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // Error msg state
    val snackbarHostState = remember { SnackbarHostState() }

    // Error msg key, used to trigger the snackbar
    var snackbarKey by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()

    val fields = listOf(
        "Item name" to item,
        "Energy(kcal)" to energy,
        "Fat(g)" to fat,
        "Carbohydrates" to carbohydrates,
        "Sugar" to sugar,
        "Fiber" to fiber,
        "Protein" to protein,
        "Salt" to salt
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
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Box(
            modifier = Modifier
                .imePadding()
                //.verticalScroll(scrollState)
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
                        fontSize = 20.sp,
                    )

                    HorizontalDivider(
                        thickness = 2.dp,
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 16.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Text(
                        text = "EAN: $ean",
                        fontSize = 16.sp,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    fields.forEach { (label, _) ->
                        TextField(
                            value = when (label) {
                                "Item name" -> item
                                "Energy(kcal)" -> energy
                                "Fat(g)" -> fat
                                "Carbohydrates" -> carbohydrates
                                "Sugar" -> sugar
                                "Fiber" -> fiber
                                "Protein" -> protein
                                "Salt" -> salt
                                else -> ""
                            },
                            onValueChange = { newValue ->
                                when (label) {
                                    "Item name" -> item = newValue
                                    "Energy(kcal)" -> energy = newValue
                                    "Fat(g)" -> fat = newValue
                                    "Carbohydrates" -> carbohydrates = newValue
                                    "Sugar" -> sugar = newValue
                                    "Fiber" -> fiber = newValue
                                    "Protein" -> protein = newValue
                                    "Salt" -> salt = newValue
                                }
                            },
                            label = { Text(label) },
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
                            onClick = {
                                navController.navigate("homeView")
                                snackbarKey = 0
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError
                            ),
                            modifier = Modifier
                                .width(140.dp)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Cancel"
                            )
                        }

                        // Add button
                        Button(
                            onClick = {
                                if (isError(
                                        item,
                                        protein,
                                        fat,
                                        carbohydrates,
                                        energy,
                                        sugar,
                                        fiber,
                                        salt
                                    )
                                ) {
                                    snackbarKey++
                                } else {
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

                                    navController.navigate("homeView")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier
                                .width(140.dp)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Save"
                            )
                        }
                    }
                }
            }

            // Shows error msg
            SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp))
        }
    }

    //Triggers msg if key is more than 0
    if (snackbarKey>0) {
        LaunchedEffect(snackbarKey) {
            scope.launch {
                snackbarHostState.showSnackbar("Do not leave any fields empty! \n" +
                        "Nutrients can only be numbers!")
            }
        }
    }
}

// A fucntion for checking if any given fields are empty or not a number
fun isError(
    itemName: String,
    protein: String,
    fat: String,
    carbohydrates: String,
    energy: String,
    sugar: String,
    fiber: String,
    salt: String
): Boolean {

    // List of fields to check
    val fieldsToCheck = listOf(
        protein,
        fat,
        carbohydrates,
        energy,
        sugar,
        fiber,
        salt
    )

    for (field in fieldsToCheck) {
        if (field.isBlank()) {
            // If any field is blank, return true
            return true
        }
        if (field.toDoubleOrNull() == null) {
            // If any field cannot be parsed as a Double, return true
            return true
        }
    }

    if (itemName.isBlank()) {
        return true
    }

    // If all fields are non-blank and can be parsed as Doubles, return false
    return false
}

@Preview
@Composable
fun PreviewForm () {
    val navController = rememberNavController()
    FoodieDiaryTheme {
        FormView(
            ean = "64707688",
            navController = navController
        )
    }
}
