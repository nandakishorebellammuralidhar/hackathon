package com.example.mqtt_interface.screens.audio_controller.player

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import java.io.File

class AndroidAudioPlayer(private val context: Context) : AudioPlayer {
    private var audioPlayer: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun createAudioPlayer(file: File): MediaPlayer {
        return MediaPlayer.create(context, file.toUri())
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun playFile(file: File) {
        createAudioPlayer(file).apply {
            audioPlayer = this
            start()
        }
    }

    override fun stop() {
        audioPlayer?.stop()
        audioPlayer?.reset()
        audioPlayer = null
    }
}