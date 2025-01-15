package com.redward.todolistandroid

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.FileOutputStream
import com.redward.todolistandroid.CameraUtils
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImagePainter

private const val TAG = "TaskScreen"

@Composable
fun TaskAddScreen(onAddClick: (Task) -> Unit, onBackClick: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var isDone by remember { mutableStateOf(false) }
    var imageUrl by remember { mutableStateOf<String?>(null) } // For TaskAddScreen
    var isUploading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var isImageLoading by remember { mutableStateOf(false) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val onImageCaptured = rememberImageCaptureHandler(
        context = context,
        onImageUrlReceived = { url ->
            imageUrl = url
            isUploading = false
        },
        onError = { error ->
            // Handle error (show snackbar, etc.)
            isUploading = false
        }
    )

    val takePictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageUri.value?.let { uri ->
                isUploading = true
                onImageCaptured(uri)
            }
        } else {
            isUploading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Add Task", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isDone,
                onCheckedChange = { isDone = it }
            )
            Text(text = "Done")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            try {
                val imageFile = CameraUtils.createImageFile(context)
                val uri = CameraUtils.getUriForFile(context, imageFile)
                imageUri.value = uri
                takePictureLauncher.launch(uri)
            } catch (e: Exception) {
                // Handle error
            }
        }, enabled = !isUploading) {
            Text(if (isUploading) "Uploading..." else "Take Picture")
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUrl?.let { url ->
            Log.d(TAG, "Displaying image from URL: $url")
            Box {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = url,
                        onState = { state ->
                            isImageLoading = state is AsyncImagePainter.State.Loading
                            if (state is AsyncImagePainter.State.Error) {
                                Log.e(TAG, "Error loading image: ${state.result}")
                            }
                        }
                    ),
                    contentDescription = "Task Image",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )
                if (isImageLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val newTask = Task("", "", title, date, isDone, imageUrl)
                onAddClick(newTask)
            },
            enabled = !isUploading
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onBackClick() }) {
            Text("Back")
        }
    }
}