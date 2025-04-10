package com.example.mqtt_interface.screens.audio_controller

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(
    displayText: String = "Recording",
    shapeType: Shape = CircleShape,
    modifier: Modifier = Modifier,
    shapeSize: Dp = 25.dp,
    shapeColor: Color = MaterialTheme.colorScheme.primary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
) {
    val shapes = listOf(

        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
    )

    shapes.forEachIndexed { index, animateable ->
        LaunchedEffect(key1 = animateable) {
            delay(index * 150L)
            animateable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 using LinearOutSlowInEasing
                        1.0f at 300 using LinearOutSlowInEasing
                        0.0f at 600 using LinearOutSlowInEasing
                        0.0f at 1200 using LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }


    val circleValues = shapes.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    val lastCircle = circleValues.size - 1

    Column(modifier = modifier) {

        Row(modifier = modifier) {
            circleValues.forEachIndexed { index, value ->
                Box(
                    modifier = Modifier
                        .size(shapeSize)
                        .graphicsLayer {
                            translationY = -value * distance
                        }
                        .background(
                            color = shapeColor,
                            shape = shapeType
                        )
                )
                if (index != lastCircle) {
                    Spacer(modifier = Modifier.width(spaceBetween))
                }
            }
        }
        Text(displayText)
    }
}