package com.nanda.drivemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nanda.drivemate.ui.theme.DrivemateTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
fun BottomBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp), // Roughly one-fifth of a 800dp screen
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.AccountCircle, contentDescription = "Microphone")
            }
            Button(onClick = { /*TODO*/ }) { Text("Button 1") }
            Button(onClick = { /*TODO*/ }) { Text("Button 2") }
            Button(onClick = { /*TODO*/ }) { Text("Button 3") }
            TextField(value = "", onValueChange = {}, placeholder = { Text("Text Box 1") })
            TextField(value = "", onValueChange = {}, placeholder = { Text("Text Box 2") })
            Text(text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DrivemateTheme {
        BottomBar()
    }
}