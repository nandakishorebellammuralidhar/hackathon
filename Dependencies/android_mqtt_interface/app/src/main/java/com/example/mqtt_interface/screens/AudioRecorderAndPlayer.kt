package com.example.mqtt_interface.screens

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.mqtt_interface.screens.audio_controller.LoadingAnimation
import com.example.mqtt_interface.screens.audio_controller.player.AndroidAudioPlayer
import com.example.mqtt_interface.screens.audio_controller.recorder.AndroidAudioRecorder
import com.example.mqtt_interface.ui.theme.Mqtt_interfaceTheme
import java.io.File


class AudioRecorderAndPlayer(var navController: NavController) : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.RECORD_AUDIO), 0
        )
        setContent {
            AudioRecordAndPlayback(applicationContext, navController)
        }
    }


}


@Composable
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun AudioRecordAndPlayback(applicationContext: Context, navController: NavController) {
    val recorder by lazy {
        AndroidAudioRecorder(applicationContext)
    }

    val player by lazy {
        AndroidAudioPlayer(applicationContext)
    }

    var audioFile: File? = null
    var isRecording by remember { mutableStateOf(false) }
    var isPlayingSound by remember { mutableStateOf(false) }
    var recordedFilePresent by remember { mutableStateOf(false) }
    val spaceBetween: Dp = 50.dp

    Mqtt_interfaceTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isPlayingSound) {

                if (isRecording) LoadingAnimation(shapeColor = Color.Red)

                Spacer(modifier = Modifier.height(spaceBetween))
                if (!isRecording) Button(onClick = {
                    File(applicationContext.cacheDir, "audio.mp3").also {
                        recorder.start(it)
                        audioFile = it
                        isRecording = true
                    }


                }) {
                    Text(text = "Start recording")
                }
                if (isRecording) Button(onClick = {
                    recorder.stop()
                    isRecording = false
                    recordedFilePresent = true
                }) {
                    // Stop recording
                    Text(text = "Stop")
                }
            }

            if (recordedFilePresent) {
                if (isPlayingSound) {
                    Spacer(modifier = Modifier.height(spaceBetween))
                    LoadingAnimation(
                        "Reproducing",
                        RectangleShape,
                        shapeColor = Color.Green
                    )
                    Spacer(modifier = Modifier.height(spaceBetween))
                }

                if (!isPlayingSound && !isRecording) Button(onClick = {
                    audioFile?.let {
                        player.playFile(it)
                        isPlayingSound = true

                    }
                }) {
                    Text(text = "Play")
                }
                if (isPlayingSound) Button(onClick = {
                    player.stop()
                    isPlayingSound = false
                }) {
                    // Stop reproducing Sound
                    Text(text = "Stop")

                }
            }
            Spacer(modifier = Modifier.height(100.dp))
            OutlinedButton(onClick = {
                recorder.stop()
                player.stop()
                navController.popBackStack()
            }) {
                Text("Go Back")
            }

        }
    }
}

