package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.models.data.entity.Item

@Composable
fun FormView(
    navContoller: NavController
){
    var item by remember {mutableStateOf("")}
    var ean by remember { mutableStateOf("978655") }
    var protein by remember {mutableStateOf("")}
    var carbohydrates by remember {mutableStateOf("")}
    var fat by remember {mutableStateOf("")}
    var energy by remember {mutableStateOf("")}


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Fill out information",
                    fontSize = 24.sp,
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                )
                Text(
                    text = "Item: ${item}", // item.name
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = protein,
                    onValueChange = { protein = it },
                    label = { Text("protein") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = fat,
                    onValueChange = { fat = it },
                    label = { Text("fat(g)") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = carbohydrates,
                    onValueChange = { carbohydrates = it },
                    label = { Text("carbohydrates") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = energy,
                    onValueChange = { energy = it },
                    label = { Text("energy(kcal)") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "EAN: ${ean}", // item.ean
                    fontSize = 18.sp,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button( // Add item -button
                        onClick = {
                            /*val newItem = Item(
                                ean = ,
                                name = item,
                                energy = energy,
                                fat = fat,
                                carbohydrates = carbohydrates,
                                sugar = 0.00,
                                fiber = 0.00,
                                protein = protein,
                                salt = 0.00
                            )
                             */
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary, // Color(0xFF7AC74F),
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        modifier = Modifier
                            .width(140.dp)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Add item"
                        )
                    }
                    Button(
                        onClick = { navContoller.popBackStack() },
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
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewForm () {
    val navController = rememberNavController()
    FormView(navController)
}

// Tiedontäyttö näkymä
//
//tuotteen nimi
//
//tuotteen EAN
//
//1 piste (6h tai vähemmän)
//
//Tiedontäyttö logiikka
//
//täytetyt tiedot tallennetaan tietokantaan oikein
//
//1 pistettä (6h tai vähemmän)