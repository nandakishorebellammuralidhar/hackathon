package com.example.mqtt_interface.screens

import android.content.Context
import android.hardware.camera2.CameraManager
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.mqtt_interface.ui_button.NavigateBackButton


@OptIn(ExperimentalCamera2Interop::class)
@Composable
fun CameraTestScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraIdList = remember { cameraManager.cameraIdList }
    var selectedCameraId by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK.toString()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraSelector = CameraSelector.Builder()
                    .addCameraFilter { cameraInfos ->
                        cameraInfos.filter { cameraInfo ->
                            val cameraId = Camera2CameraInfo.from(cameraInfo).cameraId
                            cameraId == selectedCameraId
                        }
                    }
                    .build()

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(context))

            previewView
        }, modifier = Modifier.weight(1f))

        LazyColumn(
            modifier = Modifier
                .weight(0.3f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(cameraIdList) { cameraId ->
                OutlinedButton(onClick = {
                    selectedCameraId = cameraId
                }) {
                    Text("Use Camera: $cameraId")
                }
            }
        }

        NavigateBackButton(
            maxClicks = 1,
            navController = navController
        )
    }
}


