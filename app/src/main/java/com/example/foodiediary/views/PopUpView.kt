package com.example.foodiediary.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun PopUpView(showPopup: Boolean, closePopup: () -> Unit) {
    if (showPopup) {
        Popup(
            onDismissRequest = closePopup,
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text("This is a popup!")
                Button(onClick = closePopup) {
                    Text("Close")
                }
            }
        }
    }
}