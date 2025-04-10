package com.example.mqtt_interface.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.mqtt_interface.ui_button.NavigateBackButton


private val RECORD_AUDIO_PERMISSION_REQUEST_CODE = 123

class StartSpeechToTextApp(var navController: NavController) : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.RECORD_AUDIO), 0
        )
        setContent {
            Column {
                StartSpeechToTextRecording(navController)

            }
        }
    }
}


@Composable
fun StartSpeechToTextRecording(navController: NavController?) {
    val context = LocalContext.current
    rememberCoroutineScope()
    var recognizedText by remember { mutableStateOf("") }

    val speechRecognizerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (matches != null && matches.isNotEmpty()) {
                    recognizedText = matches[0]
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(context, "Speech recognition cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Speech recognition failed", Toast.LENGTH_SHORT).show()
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(350.dp))
        if (navController != null) {
            NavigateBackButton(navController)
        }
        Text(
            text = recognizedText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = {
                startSpeechRecognition(context, speechRecognizerLauncher)
            },
            modifier = Modifier.fillMaxWidth(),

            ) {
            Text("Speak Now")
        }
        Spacer(modifier = Modifier.height(100.dp))
        OutlinedButton(
            onClick = { navController?.popBackStack() }
        ) {
            Text("back")
        }

    }
}

private fun startSpeechRecognition(context: Context, launcher: ActivityResultLauncher<Intent>) {


    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            RECORD_AUDIO_PERMISSION_REQUEST_CODE
        )
    } else {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now ...")
                // Change here the language that should be detected!!!!!!
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "de-DE")
            }
            try {
                launcher.launch(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Error starting speech recognition: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                context,
                "Speech recognition is not available on this device",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StartSpeechToTextRecording(null)
}
