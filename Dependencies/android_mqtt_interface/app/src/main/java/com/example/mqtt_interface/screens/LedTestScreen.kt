package com.example.mqtt_interface.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject


@Composable
fun LedTestScreen() {
    val coroutineScopeBlueButton = rememberCoroutineScope()
    val coroutineScopeRedButton = rememberCoroutineScope()
    val coroutineScopeGreenButton = rememberCoroutineScope()
    val coroutineScopeAccentureButton = rememberCoroutineScope()
    val coroutineScopeDiscoButton = rememberCoroutineScope()
    val colors = listOf(
        Color.Blue, Color(0, 100, 190), Color.Red, Color.Green, Color.Magenta, Color.Yellow,
        Color.Cyan, Color.Magenta, Color(122, 33, 255)
    )
    val text = "LED TEST MENU"
    val styledText = buildAnnotatedString {
        text.forEachIndexed { index, char ->
            withStyle(style = SpanStyle(color = colors[index % colors.size])) {
                append(char)
            }
        }
    }
    val blueButtonContent: String =
        """{"on":true,"bri":255,"transition":7,"ps":-1,"pl":-1,"AudioReactive":{"on":true},"nl":{"on":false,"dur":60,"mode":1,"tbri":0,"rem":-1},"udpn":{"send":false,"recv":true,"sgrp":1,"rgrp":1},"lor":0,"mainseg":0,"seg":[{"id":0,"start":0,"stop":20,"len":20,"grp":1,"spc":0,"of":0,"on":true,"frz":false,"bri":255,"cct":127,"set":0,"col":[[0,0,255],[0,0,0],[0,0,0]],"fx":0,"sx":128,"ix":128,"pal":0,"c1":128,"c2":128,"c3":16,"sel":true,"rev":false,"mi":false,"o1":false,"o2":false,"o3":false,"si":0,"m12":0}]}"""
    val redButtonContent: String =
        """{"on":true,"bri":255,"transition":7,"ps":-1,"pl":-1,"AudioReactive":{"on":true},"nl":{"on":false,"dur":60,"mode":1,"tbri":0,"rem":-1},"udpn":{"send":false,"recv":true,"sgrp":1,"rgrp":1},"lor":0,"mainseg":0,"seg":[{"id":0,"start":0,"stop":20,"len":20,"grp":1,"spc":0,"of":0,"on":true,"frz":false,"bri":255,"cct":127,"set":0,"col":[[255,0,0],[0,0,0],[0,0,0]],"fx":0,"sx":128,"ix":128,"pal":0,"c1":128,"c2":128,"c3":16,"sel":true,"rev":false,"mi":false,"o1":false,"o2":false,"o3":false,"si":0,"m12":0}]}"""
    val greenButtonContent: String =
        """{"on":true,"bri":255,"transition":7,"ps":-1,"pl":-1,"AudioReactive":{"on":true},"nl":{"on":false,"dur":60,"mode":1,"tbri":0,"rem":-1},"udpn":{"send":false,"recv":true,"sgrp":1,"rgrp":1},"lor":0,"mainseg":0,"seg":[{"id":0,"start":0,"stop":20,"len":20,"grp":1,"spc":0,"of":0,"on":true,"frz":false,"bri":255,"cct":127,"set":0,"col":[[8,255,0],[0,0,0],[0,0,0]],"fx":0,"sx":128,"ix":128,"pal":0,"c1":128,"c2":128,"c3":16,"sel":true,"rev":false,"mi":false,"o1":false,"o2":false,"o3":false,"si":0,"m12":0}]}"""
    val accButtonContent: String =
        """{"on":true,"bri":255,"transition":7,"ps":-1,"pl":-1,"AudioReactive":{"on":true},"nl":{"on":false,"dur":60,"mode":1,"tbri":0,"rem":-1},"udpn":{"send":false,"recv":true,"sgrp":1,"rgrp":1},"lor":0,"mainseg":0,"seg":[{"id":0,"start":0,"stop":20,"len":20,"grp":1,"spc":0,"of":0,"on":true,"frz":false,"bri":255,"cct":127,"set":0,"col":[[255,0,255],[0,0,0],[0,0,0]],"fx":0,"sx":128,"ix":128,"pal":0,"c1":128,"c2":128,"c3":16,"sel":true,"rev":false,"mi":false,"o1":false,"o2":false,"o3":false,"si":0,"m12":0}]}"""
    val accDiscoContent: String =
        """{"on":true,"bri":255,"transition":7,"ps":-1,"pl":-1,"AudioReactive":{"on":true},"nl":{"on":false,"dur":60,"mode":1,"tbri":0,"rem":-1},"udpn":{"send":false,"recv":true,"sgrp":1,"rgrp":1},"lor":0,"mainseg":0,"seg":[{"id":0,"start":0,"stop":20,"len":20,"grp":1,"spc":0,"of":0,"on":true,"frz":false,"bri":255,"cct":127,"set":0,"col":[[255,0,255],[0,0,0],[0,0,0]],"fx":28,"sx":128,"ix":128,"pal":1,"c1":128,"c2":128,"c3":16,"sel":true,"rev":false,"mi":false,"o1":false,"o2":false,"o3":false,"si":0,"m12":0}]}"""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(top = 10.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = styledText,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(100.dp)
        )

        Button(
            colors = ButtonDefaults.buttonColors(Color.Blue),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(350.dp),
            onClick = {
                coroutineScopeBlueButton.launch {
                    val requestBody = parseJsonString(blueButtonContent)
                    sendPostRequest(requestBody)
                }
            }
        ) {
            Text(
                text = "Blue",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(Color.Red),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(350.dp),
            onClick = {
                coroutineScopeRedButton.launch {
                    val requestBody = parseJsonString(redButtonContent)
                    sendPostRequest(requestBody)
                }

            }
        ) {
            Text(
                text = "Red",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(Color.Green),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(350.dp),
            onClick = {
                coroutineScopeGreenButton.launch {
                    val requestBody = parseJsonString(greenButtonContent)
                    sendPostRequest(requestBody)
                }
            }
        ) {
            Text(
                text = "Green",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(Color.Magenta),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(350.dp),
            onClick = {
                coroutineScopeAccentureButton.launch {
                    val requestBody = parseJsonString(accButtonContent)
                    sendPostRequest(requestBody)
                }
            }
        ) {
            Text(
                text = "Accenture",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(350.dp),
            onClick = {
                coroutineScopeDiscoButton.launch {
                    val requestBody = parseJsonString(accDiscoContent)
                    sendPostRequest(requestBody)
                }
            }
        ) {
            Text(
                text = "Disco",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}


suspend fun sendPostRequest(body: JsonObject) {
    val url = "http://172.20.10.8/json/state"
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    try {
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        println("Response: $response")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        client.close()
    }
}

fun parseJsonString(jsonString: String): JsonObject {
    val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
    return json.parseToJsonElement(jsonString).jsonObject
}


@Preview(showBackground = true)
@Composable
fun PreviewLedScreen() {
    LedTestScreen()
}