package com.nanda.drivemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nanda.drivemate.ui.theme.DrivemateTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrivemateTheme {
                Scaffold(
                    bottomBar = { BottomBar() }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Your main content here")
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(moveUpPixels: Dp = 50.dp, updateIntervalMillis: Long = 3000) {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    var co2Level by remember { mutableStateOf(700) }
    var temperature by remember { mutableStateOf(7.3f) }
    var batteryLevel by remember { mutableStateOf(47) }
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getCurrentTime()
            co2Level = Random.nextInt(600, 800) // Example random CO2
            temperature = Random.nextFloat() * 5 + 5 // Example random temperature
            batteryLevel = Random.nextInt(30, 60) // Example random battery
            delay(updateIntervalMillis)
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .offset(y = -moveUpPixels),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start
            ) {
                ButtonWithText(
                    topText = "CO2",
                    bottomText = getCO2String(co2Level),
                    sizeType = 1,
                    onClickIndex = 1
                )
                Spacer(modifier = Modifier.width(8.dp))
                ButtonWithText(
                    topText = "Temperature",
                    bottomText = "%.1f°".format(temperature),
                    sizeType = 2,
                    onClickIndex = 2
                )
                Spacer(modifier = Modifier.width(8.dp))
                ButtonWithText(
                    topText = "Battery",
                    bottomText = "$batteryLevel%",
                    sizeType = 2,
                    onClickIndex = 3
                )
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.AccountCircle, contentDescription = "Microphone")
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(width = screenWidthDp*2/5, height = 60.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Button Text")
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text(text = currentTime)
            }
        }
    }
}

@Composable
fun ButtonWithText(topText: String, bottomText: String, sizeType: Int, onClickIndex: Int) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val buttonWidth: Dp = if (sizeType == 1) {
        screenWidthDp / 10
    } else {
        screenWidthDp / 5
    }
    val bottomTextStyle: TextStyle = if(onClickIndex == 1){
        MaterialTheme.typography.titleSmall
    } else {
        MaterialTheme.typography.titleMedium
    }

    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.size(width = buttonWidth, height = 60.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = topText,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = bottomText,
                style = bottomTextStyle,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Visible
            )
        }
    }
}

fun getCO2String(co2Level: Int): String {
    return co2Level.toString() +"ppm"
}



//@Composable
//fun ButtonWithText(topText: String, bottomText: String) {
//    Button(
//        onClick = { /*TODO*/ },
//        modifier = Modifier.size(width = 80.dp, height = 60.dp),
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = topText,
//                style = MaterialTheme.typography.bodySmall,
//                textAlign = TextAlign.Center
//            )
//            Text(
//                text = bottomText,
//                style = MaterialTheme.typography.titleMedium,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Calendar.getInstance().time)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DrivemateTheme {
        BottomBar()
    }
}