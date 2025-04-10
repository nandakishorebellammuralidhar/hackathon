package com.example.mqtt_interface


import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mqtt_interface.screens.AudioRecordAndPlayback
import com.example.mqtt_interface.screens.CameraTestScreen
import com.example.mqtt_interface.screens.LedTestScreen
import com.example.mqtt_interface.screens.StartSpeechToTextRecording
import com.example.mqtt_interface.screens.TestAppMqttMessenger

class StartTestApp : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.RECORD_AUDIO), 0
        )
        setContent {
            AppNavigation(applicationContext)
        }
    }
}


@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(top = 10.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Test App Main Menu",
            color = Color.Black,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(100.dp)
        )

        Button(
            colors = ButtonDefaults.buttonColors(Color.Gray),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(350.dp),
            onClick = {
                navController.navigate("mqttMessenger")
            }
        ) {
            Text(
                text = "Mqtt-Broker Test",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(Color.Gray),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(350.dp),
            onClick = {
                navController.navigate("recordAudio")
            }
        ) {
            Text(
                text = "Record Audio",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(Color.Gray),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(350.dp),
            onClick = {
                navController.navigate("speechToText")
            }
        ) {
            Text(
                text = "Speech To Text",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(Color.Gray),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(350.dp),
            onClick = {
                navController.navigate("cameraTest")
            }
        ) {
            Text(
                text = "Camera Test",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

        }
        Button(
            colors = ButtonDefaults.buttonColors(Color.Gray),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(350.dp),
            onClick = {
                navController.navigate("ledTest")
            }
        ) {
            Text(
                text = "LED Test",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

        }

    }
}


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun AppNavigation(applicationContext: Context?) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("mqttMessenger") { TestAppMqttMessenger() }
        if (applicationContext != null) {
            composable("recordAudio") {
                AudioRecordAndPlayback(applicationContext = applicationContext, navController)
            }
        }
        composable("speechToText") { StartSpeechToTextRecording(navController) }
        composable("cameraTest") { CameraTestScreen() }
        composable("ledTest") { LedTestScreen() }
    }
}


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppNavigation(null)
}