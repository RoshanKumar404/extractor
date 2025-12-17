package com.example.extractor

import android.R
import android.content.Context
import android.net.Uri
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
//      this is the title   MyButton()
        Text(
            text = "Image Text Extractor",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
//image(modifier)
        AppImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
//                .size(200.dp)
//                .padding(top=8.dp)
//                .padding(bottom = 98.dp)
        )

 Spacer(modifier= Modifier.weight(1f))


        selectedImageUri?.let{
            DisplayImage(it)
        }
        if(isProcessing){
            CircularProgressIndicator()
        }
        if (extractedText.isNotEmpty()){
            //****this is the code for copying the code without selection*****
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
            Card (
                modifier= Modifier.fillMaxWidth(),
                shape=RoundedCornerShape(16.dp)
            ){
                Column(modifier=Modifier.padding(16.dp)) {
                    CopySelectedText(extractedText=extractedText)
                }
            }

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(29.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PickImageFromGallery { uri ->
                    selectedImageUri = uri
                    extractedText = ""
                    isProcessing = true
                    runOCR(context, uri) {
                        extractedText = it
                        isProcessing = false
                    }
                }

                CaptureImageFromCamera { uri ->
                    selectedImageUri = uri
                    extractedText = ""
                    isProcessing = true
                    runOCR(context, uri) {
                        extractedText = it
                        isProcessing = false
                    }
                }
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

@Composable
fun AppImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = com.example.extractor.R.drawable.outline_animated_images_24),
        //painter = painterResource(R.drawable.alert_dark_frame),
        contentDescription = "App Icon",
        modifier = modifier.size(196.dp)
    )
}


//@Preview(showBackground = true)
//@Composable
//fun AppImagePreview() {
//    AppImage()
//}

