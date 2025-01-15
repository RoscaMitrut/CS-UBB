package com.redward.todolistandroid

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import android.app.Activity
import android.content.Context
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberImageCaptureHandler(
    context: Context,
    onImageUrlReceived: (String?) -> Unit,
    onError: (String) -> Unit
): (Uri) -> Unit {
    return { uri ->
        try {
            val savedFile = CameraUtils.saveImageFromUri(context, uri)
            CameraUtils.uploadImage(
                file = savedFile,
                onSuccess = { url ->
                    onImageUrlReceived(url)
                },
                onError = { error ->
                    onError(error)
                }
            )
        } catch (e: Exception) {
            onError("Failed to process image: ${e.message}")
        }
    }
}

@Composable
fun rememberCameraLauncher(
    onImageCaptured: (Uri) -> Unit,
    onError: (String) -> Unit
): androidx.compose.runtime.MutableState<Uri?> {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                imageUri.value?.let { uri ->
                    onImageCaptured(uri)
                }
            } else {
                onError("Failed to capture image")
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            imageUri.value?.let { uri ->
                cameraLauncher.launch(uri)
            }
        } else {
            onError("Camera permission denied")
        }
    }

    fun launchCamera(uri: Uri) {
        when {
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                cameraLauncher.launch(uri)
            }
            (context as? Activity)?.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) == true -> {
                onError("Camera permission is required")
            }
            else -> {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    if (imageUri.value != null) {
        launchCamera(imageUri.value!!)
        imageUri.value = null
    }

    return imageUri
}