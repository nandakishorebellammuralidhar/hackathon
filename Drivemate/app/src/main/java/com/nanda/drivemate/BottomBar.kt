package com.nanda.drivemate

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
//import androidx.compose.ui.text.intl.Locale
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.format

//@Composable
//fun BottomBar() {
//    // ... other code ...
//    var currentTime by remember { mutableStateOf(getCurrentTime()) }
//
//    LaunchedEffect(Unit) {
//        while (true) {
//            currentTime = getCurrentTime()
//            delay(60_000) // Update every minute
//        }
//    }
//
//    // ... inside the Row ...
//    Text(text = currentTime)
//}
//
//fun getCurrentTime(): String {
//    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
//    return sdf.format(Calendar.getInstance().time)
//}