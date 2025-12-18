package com.example.extractor

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun CaptureImageFromCamera(
    onImageCaptured: (Uri) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri?.let(onImageCaptured)
        }
    }

    // Permission launcher
    // this was the thing that was missing , thats why my camera function was not working
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val file = File.createTempFile(
                "camera_image_",
                ".jpg",
                context.cacheDir
            )

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            imageUri = uri
            cameraLauncher.launch(uri)
        }
    }

    Button(
        onClick = {
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    ) {
        Text("Capture Image From Camera")
    }
}
