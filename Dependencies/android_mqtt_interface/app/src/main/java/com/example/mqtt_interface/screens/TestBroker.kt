package com.example.mqtt_interface.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mqtt_interface.ui.theme.TextBoxMqtt
import com.example.mqtt_interface.ui_button.NavigateBackButton
import mqtt_subscriber.MqttTopics
import mqtt_subscriber.createMqttSession
import org.eclipse.paho.client.mqttv3.MqttClient

val BROKER: MqttClient? = createMqttSession()

fun subscribeAndUpdate(broker: MqttClient?, topic: String, updateValue: (String) -> Unit) {
    if(broker == null){
        return
    }
    broker.subscribe(topic) { _, message ->
        val payload = message.payload.toString(Charsets.UTF_8)
        updateValue(payload)
    }
}

fun connected(broker: MqttClient?): String {
    if(broker == null){
        return " - Disconnected from MQTT-Broker. "
    }
    return " - Connected. "
}

@Composable
fun TestAppMqttMessenger(navController: NavController) {
    var counter: String by remember { mutableStateOf("") }
    var temperature: String by remember { mutableStateOf("") }
    var gyroscope: String by remember { mutableStateOf("") }
    var anyTopicDebug: String by remember { mutableStateOf("") }
    var distanceSensorFrontLeft: String by remember { mutableStateOf("") }
    var distanceSensorRearLeft: String by remember { mutableStateOf("") }
    var distanceSensorFrontRight: String by remember { mutableStateOf("") }
    var distanceSensorRearRight: String by remember { mutableStateOf("") }


    subscribeAndUpdate(BROKER, MqttTopics.TestCounter.topicName) { value ->
        counter = value
    }
    subscribeAndUpdate(BROKER, MqttTopics.TEMPERATURE_SENSOR_REAR_RIGHT.topicName) { value ->
        temperature = value
    }

    subscribeAndUpdate(BROKER, MqttTopics.GYROSCOPE_SENSOR_REAR_RIGHT.topicName) { value ->
        gyroscope = value
    }

    subscribeAndUpdate(BROKER, MqttTopics.ANY.topicName) { value ->
        anyTopicDebug = value
    }
    subscribeAndUpdate(BROKER, MqttTopics.DISTANCE_SENSOR_FRONT_LEFT.topicName) { value ->
        distanceSensorFrontLeft = value
    }
    subscribeAndUpdate(BROKER, MqttTopics.DISTANCE_SENSOR_REAR_LEFT.topicName) { value ->
        distanceSensorRearLeft = value
    }

    subscribeAndUpdate(BROKER, MqttTopics.DISTANCE_SENSOR_FRONT_RIGHT.topicName) { value ->
        distanceSensorFrontRight = value
    }
    subscribeAndUpdate(BROKER, MqttTopics.DISTANCE_SENSOR_REAR_RIGHT.topicName) { value ->
        distanceSensorRearRight = value
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "MQTT Broker Test App ${connected(BROKER)}",
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .height(100.dp),

                )
        }
        item { NavigateBackButton(navController) }
        item { TextBoxMqtt("Temperature: $temperature °C") }
        item { TextBoxMqtt("Gyroscope: $gyroscope") }
        item { TextBoxMqtt("Distance Sensor Front Left: : $distanceSensorFrontLeft ") }
        item { TextBoxMqtt("Distance Sensor Rear Left: : $distanceSensorRearLeft ") }
        item { TextBoxMqtt("Distance Sensor Front Right: : $distanceSensorFrontRight ") }
        item { TextBoxMqtt("Distance Sensor Rear Right: : $distanceSensorRearRight ") }
        item { TextBoxMqtt("Debug-Any-Topic: : $anyTopicDebug ") }
    }
}

