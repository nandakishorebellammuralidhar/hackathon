package com.example.mqtt_interface.screens.audio_controller.player

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}