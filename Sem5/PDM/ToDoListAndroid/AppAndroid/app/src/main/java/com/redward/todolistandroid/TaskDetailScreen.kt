package com.redward.todolistandroid

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImagePainter
import com.redward.todolistandroid.CameraUtils

private const val TAG = "TaskScreen"

@Composable
fun TaskDetailScreen(task: Task, onEditClick: (Task) -> Unit, onBackClick: () -> Unit) {
    var title by remember { mutableStateOf(task.title) }
    var date by remember { mutableStateOf(task.date) }
    var isDone by remember { mutableStateOf(task.isDone) }
    var isUploading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var imageUrl by remember { mutableStateOf(task.imageUrl) }
    var isImageLoading by remember { mutableStateOf(false) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val onImageCaptured = rememberImageCaptureHandler(
        context = context,
        onImageUrlReceived = { url ->
            imageUrl = url
            isUploading = false
        },
        onError = { error ->
            isUploading = false
            errorMessage = error
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
            errorMessage = "Failed to capture image"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Task Details", style = MaterialTheme.typography.h4)

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

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isDone,
                onCheckedChange = { isDone = it }
            )
            Text(text = "Done")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                try {
                    val imageFile = CameraUtils.createImageFile(context)
                    val uri = CameraUtils.getUriForFile(context, imageFile)
                    imageUri.value = uri
                    takePictureLauncher.launch(uri)
                } catch (e: Exception) {
                    errorMessage = "Failed to initialize camera: ${e.message}"
                }
            },
            enabled = !isUploading
        ) {
            Text(if (isUploading) "Uploading..." else "Take Picture")
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUrl?.let { url ->
            Box {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = url,
                        onState = { state ->
                            isImageLoading = state is AsyncImagePainter.State.Loading
                            if (state is AsyncImagePainter.State.Error) {
                                errorMessage = "Error loading image"
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

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val updatedTask = task.copy(
                    title = title,
                    date = date,
                    isDone = isDone,
                    imageUrl = imageUrl
                )
                onEditClick(updatedTask)
            },
            enabled = !isUploading
        ) {
            Text("Save Changes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onBackClick() }) {
            Text("Back")
        }
    }
}