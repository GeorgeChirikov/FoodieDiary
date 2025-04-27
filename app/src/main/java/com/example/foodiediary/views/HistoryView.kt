package com.example.foodiediary.views

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodiediary.ui.theme.AppleRed
import com.example.foodiediary.ui.theme.GrassGreen
import com.example.foodiediary.ui.theme.LightGreen
import com.example.foodiediary.utils.HistoryViewModelFactory
import com.example.foodiediary.viewmodels.HistoryViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryView(navController: NavController) {
    val viewModel: HistoryViewModel = viewModel( factory = HistoryViewModelFactory(LocalContext.current))
    val selectedDate by viewModel.selectedDate.collectAsState()
    val filteredData by viewModel.filteredData.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),
    )

    Column(modifier = Modifier
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    AppleRed,
                    LightGreen,
                    GrassGreen
                )
            )
        )
        .fillMaxSize()) {
        Button(onClick = { showDatePicker = true }) {
            Text(text = "Pick Date")
        }
        Text(
            text = "Selected Date: ${selectedDate.format(DateTimeFormatter.ISO_DATE)}",
            modifier = Modifier.padding(16.dp)
        )

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