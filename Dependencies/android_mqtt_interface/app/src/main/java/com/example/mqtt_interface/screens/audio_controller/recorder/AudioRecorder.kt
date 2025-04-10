package com.example.mqtt_interface.screens.audio_controller.recorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}