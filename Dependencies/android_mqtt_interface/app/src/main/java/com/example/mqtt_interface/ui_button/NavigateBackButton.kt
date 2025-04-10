package com.example.mqtt_interface.ui_button

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NavigateBackButton(navController: NavController, maxClicks: Int = 1) {
    var clickCount by remember { mutableStateOf(0) }
    var isButtonEnabled by remember { mutableStateOf(true) }

    OutlinedButton(
        onClick = {
            if (clickCount < maxClicks) {
                clickCount++
                navController.popBackStack()
                if (clickCount >= maxClicks) {
                    isButtonEnabled = false
                }
            }
        },
        enabled = isButtonEnabled,
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Go Back")
    }
}