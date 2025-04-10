package com.example.mqtt_interface.screens


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mqtt_interface.ui.theme.TextBoxMqtt
import mqtt_subscriber.MqttTopics
import mqtt_subscriber.createMqttSession
import org.eclipse.paho.client.mqttv3.MqttClient


class StartBrokerApp : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            TestAppMqttMessenger()
        }
    }
}


@Composable
fun TestAppMqttMessenger() {
    lateinit var broker: MqttClient
    var counter: String by remember { mutableStateOf("") }
    var temperature: String by remember { mutableStateOf("") }
    var sovLevel: String by remember { mutableStateOf("") }
    broker = createMqttSession()

    broker.subscribe(MqttTopics.TestCounter.topicName) { _, message ->
        println("Message received: ${message.payload.toString(Charsets.UTF_8)}")
        counter = message.payload.toString(Charsets.UTF_8)
    }
    broker.subscribe(MqttTopics.ClimateTemp.topicName) { _, message ->
        println("Message received: ${message.payload.toString(Charsets.UTF_8)}")
        temperature = message.payload.toString(Charsets.UTF_8)
    }
    broker.subscribe(MqttTopics.BmsSoc.topicName) { _, message ->
        println("Message received: ${message.payload.toString(Charsets.UTF_8)}")
        sovLevel = message.payload.toString(Charsets.UTF_8)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "MQTT Broker Test App",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(100.dp)
        )
        TextBoxMqtt("Test Counter: $counter #")
        TextBoxMqtt("Temperature: $temperature °C")
        TextBoxMqtt("SOC Level: : $sovLevel %")


    }
}

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    TestAppMqttMessenger()
}