package com.example.extractor

import android.view.inputmethod.ExtractedText
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CopySelectedText(
    extractedText: String,
    modifier: Modifier= Modifier
){
    val context= LocalContext.current
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = extractedText))
    }
    Column (modifier=modifier){
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {textFieldValue=it},
            modifier=Modifier.fillMaxWidth(),
            readOnly = true,
            label = { Text("Extracted Text") }
        )
        Spacer(modifier= Modifier.height(8.dp))
        Button(
            onClick={
                val Selection= textFieldValue.selection
                val  selectedText=
                    if(Selection.start!=Selection.end){
                        textFieldValue.text.substring(
                            Selection.start,
                            Selection.end
                        )

                    }
                    else ""
                if (selectedText.isNotEmpty()) {
                    val clipboard =
                        context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)
                                as android.content.ClipboardManager
                    val clip = android.content.ClipData.newPlainText(
                        "Selected OCR Text",
                        selectedText
                    )
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Selected text Copied", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Please select text first", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Copy the selected text")
        }
    }

}