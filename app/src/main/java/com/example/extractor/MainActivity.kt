package com.example.extractor

import android.R
import android.content.Context
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
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

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
    var extractedText by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
//        MyButton()



        selectedImageUri?.let{
            DisplayImage(it)
        }
        if(isProcessing){
            CircularProgressIndicator()
        }
        if (extractedText.isNotEmpty()){
//            Text(
//                text = "Extracted Text: \n$extractedText",
//                style = MaterialTheme.typography.bodyMedium
//            )
//            Button(
//                onClick={
//                    val clipboard=
//                        context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)
//                    as android.content.ClipboardManager
//                    val clip= android.content.ClipData.newPlainText(
//                        "OCR Text",
//                        extractedText
//                    )
//                    clipboard.setPrimaryClip(clip)
//                    Toast.makeText(context,"Text copied to clipboard", Toast.LENGTH_SHORT).show()
//                }
//            ){
//                Text("Copy Extracted TextText")
//            }
            CopySelectedText(extractedText=extractedText)
        }
        PickImageFromGallery { uri ->
            selectedImageUri=uri
            // Toast.makeText(context, "Image selected:\n$uri", Toast.LENGTH_SHORT).show()
            // Later → send this URI to ML Kit OCR
            extractedText=""
            isProcessing=true
            runOCR(context,uri){text->
                extractedText=text
                isProcessing=false
            }
           // isProcessing=false
        }
        CaptureImageFromCamera { uri ->
            selectedImageUri=uri
            extractedText=""
            isProcessing=true
            runOCR(context,uri){
                extractedText=it
                isProcessing= false
            }
        }
    }
}
fun runOCR(
    context: Context,
    uri:Uri,
    onResult: (String)-> Unit
){
    val image= InputImage.fromFilePath(context,uri)
    val  recognizer= TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    recognizer.process(image)
        .addOnSuccessListener{visionText->
            onResult(visionText.text)
        }
        .addOnFailureListener {
            onResult("OCR Failed : %{it.message}")
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
