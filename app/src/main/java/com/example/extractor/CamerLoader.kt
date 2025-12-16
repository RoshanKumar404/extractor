package com.example.extractor

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun CaptureImageFromCamera(
    onImageCaptured:(Uri)-> Unit
){
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        success->
        if (success){
            imageUri?.let{onImageCaptured(it)}
        }
    }
    Button(onClick = {
        val file= File.createTempFile(
            "camera_image_",
            ".jpg",
            context.cacheDir
        )
        val uri= FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        imageUri=uri
        launcher.launch(uri)
    }) {
        Text("Capture Image From Camera")
    }

}