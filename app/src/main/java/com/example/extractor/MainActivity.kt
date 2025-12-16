package com.example.extractor

import android.net.Uri
import android.os.Bundle
import android.view.Display
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.extractor.ui.theme.ExtractorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExtractorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
//        MyButton()

        PickImageFromGallery { uri ->
            selectedImageUri=uri
            Toast.makeText(context, "Image selected:\n$uri", Toast.LENGTH_SHORT).show()
            // Later → send this URI to ML Kit OCR
        }
        selectedImageUri?.let{uri->
            DisplayImage(uri)
        }
    }
}

@Composable
fun DisplayImage(uri: Uri) {
//    TODO("Not yet implemented")
    Image(
        painter = rememberAsyncImagePainter(uri),
        contentDescription = "Selected Image",
       modifier = Modifier
           .fillMaxWidth()
           .height(250.dp)
    )
}


@Composable
fun MyButton() {
    val context = LocalContext.current

    Button(
        onClick = {
            Toast.makeText(context, "Jai Sri Ram 🚩", Toast.LENGTH_SHORT).show()
        },
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.White
        )
    ) {
        Text("Click Me")
    }
}

@Composable
fun PickImageFromGallery(
    onImagePicked: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onImagePicked(it) }
    }

    Button(onClick = { launcher.launch("image/*") }) {
        Text("Pick Image From Gallery")
    }
}
