package com.nanda.drivemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
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
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedGraph by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top part: Graph display area
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), // Adjust height as needed
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            if (selectedGraph != null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    GraphContent(title = "$selectedGraph Graph") // Your graph content
                    IconButton(
                        onClick = { selectedGraph = null },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Close Graph")
                    }
                }
            } else {
                Box(contentAlignment = Alignment.Center) {
                    Text("Select a graph below")
                }
            }
        }
        // Bottom part: Bottom Bar
        BottomBar(onGraphSelected = { selectedGraph = it })
    }
}

@Composable
fun GraphContent(title: String) {
    // Replace this with your actual graph drawing composable
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Graph for $title")
    }
}

@Composable
fun BottomBar(
    moveUpPixels: Dp = 50.dp,
    updateIntervalMillis: Long = 3000,
    onGraphSelected: (String?) -> Unit
) {
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
                    onClick = { onGraphSelected("CO2") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                ButtonWithText(
                    topText = "Temperature",
                    bottomText = "%.1f°".format(temperature),
                    sizeType = 2,
                    onClick = { onGraphSelected("Temperature") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                ButtonWithText(
                    topText = "Battery",
                    bottomText = "$batteryLevel%",
                    sizeType = 2,
                    onClick = { onGraphSelected("Battery") }
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
                    modifier = Modifier.size(width = screenWidthDp * 2 / 5, height = 60.dp),
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
fun ButtonWithText(
    topText: String,
    bottomText: String,
    sizeType: Int,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val buttonWidth: Dp = if (sizeType == 1) {
        screenWidthDp / 10
    } else {
        screenWidthDp / 5
    }
    val bottomTextStyle: TextStyle = TextStyle(
        fontSize = 10.sp,
        textAlign = TextAlign.Center,
    )

    Button(
        onClick = onClick,
        modifier = Modifier.size(width = buttonWidth, height = 60.dp),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = topText,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Visible
            )
            Text(
                text = bottomText,
                style = bottomTextStyle,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Visible,
                softWrap = true
            )
        }
    }
}

fun getCO2String(co2Level: Int): String {
    return co2Level.toString() + "ppm"
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Calendar.getInstance().time)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DrivemateTheme {
        MainScreen()
    }
}