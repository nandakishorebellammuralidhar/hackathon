package com.example.mqtt_interface.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mqtt_interface.ui_button.NavigateBackButton
import java.util.Locale

@Composable
fun TextToSpeechScreen(navController: NavController) {
    var isSpeaking by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val textToSpeech = rememberTextToSpeech()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Enter text to speak") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (textToSpeech.value?.isSpeaking() == true) {
                    textToSpeech.value?.stop()
                    isSpeaking = false
                } else {
                textToSpeech.value?.speak(inputText.text, TextToSpeech.QUEUE_FLUSH, null, null)
                    isSpeaking = true
                }
                       },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Speak")
        }
        NavigateBackButton(navController)
    }
}


@Composable
fun rememberTextToSpeech(): MutableState<TextToSpeech?> {
    val context = LocalContext.current
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }
    DisposableEffect(context) {
        val textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = Locale.US
            }
        }
        tts.value = textToSpeech

        onDispose {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
    return tts
}
