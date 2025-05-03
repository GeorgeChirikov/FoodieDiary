package com.example.foodiediary.views

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiediary.ui.theme.AppleRed
import com.example.foodiediary.ui.theme.FoodieDiaryTheme
import com.example.foodiediary.ui.theme.GradientBackground
import com.example.foodiediary.ui.theme.GrassGreen
import com.example.foodiediary.ui.theme.LightGreen
import com.example.foodiediary.utils.DiaryViewModelFactory
import com.example.foodiediary.viewmodels.DiaryViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryView(navController: NavController) {
    val viewModel: DiaryViewModel = viewModel( factory = DiaryViewModelFactory(LocalContext.current))
    val selectedDate by viewModel.selectedDate.collectAsState()
    val filteredData by viewModel.filteredData.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),
    )

    Column(modifier = Modifier
        .background(GradientBackground)
        .fillMaxSize()){

        // Header
        Text(
            text = "Diary",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 36.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Button to pick a date
        Button(onClick = { showDatePicker = true },
            modifier = Modifier
                .padding(start = 24.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(text = "Pick Date")
        }
        // Card to display selected date
        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .shadow(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Selected Date: ${selectedDate.format(DateTimeFormatter.ISO_DATE)}",
                modifier = Modifier.padding(16.dp)
            )}

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val newDate = Instant.ofEpochMilli(it)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            viewModel.updateSelectedDate(newDate)
                        }
                        showDatePicker = false
                    }) {
                        Text(text = "Confirm")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                )
            }
        }
        // Card to display filtered data
        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .shadow(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface),
            shape = MaterialTheme.shapes.medium
        ) {
            // Card's content
            if (filteredData.isEmpty()) {
                Text("No items available", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn {
                    items(filteredData.size) { index ->
                        val key = filteredData[index].timeStamp
                        val date = Instant.ofEpochMilli(key)

                        Text(
                            text = "$key - $date",
                            modifier = Modifier.padding(16.dp).clickable {
                                navController.navigate("popupView/${filteredData[index].ean}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview(showBackground = true)
@Composable
fun DiaryViewPreview() {
    val navController = rememberNavController()
    FoodieDiaryTheme {
        DiaryView(navController = navController)
    }
}