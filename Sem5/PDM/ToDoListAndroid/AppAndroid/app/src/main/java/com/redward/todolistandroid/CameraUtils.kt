package com.redward.todolistandroid

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object CameraUtils {
    private const val TAG = "CameraUtils"
    private const val AUTHORITY_SUFFIX = ".fileprovider"
    private const val IMAGE_QUALITY = 85 // JPEG quality (0-100)

    fun getUriForFile(context: Context, file: File): Uri {
        Log.d(TAG, "Generating URI for file: ${file.absolutePath}")
        try {
            val authority = "${context.packageName}$AUTHORITY_SUFFIX"
            Log.d(TAG, "Using authority: $authority")
            val uri = FileProvider.getUriForFile(
                context,
                authority,
                file
            )
            Log.d(TAG, "Generated URI: $uri")
            return uri
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Error generating URI for file", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error generating URI for file", e)
            throw e
        }
    }

    fun createImageFile(context: Context): File {
        Log.d(TAG, "Creating new image file")
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val file = File.createTempFile(
            imageFileName,
            ".jpg",
            context.cacheDir
        ).apply {
            deleteOnExit()
        }
        Log.d(TAG, "Created image file: ${file.absolutePath}")
        return file
    }

    fun saveImageFromUri(context: Context, uri: Uri): File {
        val file = createImageFile(context)
        try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                val bitmap = android.graphics.BitmapFactory.decodeStream(input)
                val scaledBitmap = if (bitmap.width > 1024 || bitmap.height > 1024) {
                    val scale = 1024f / maxOf(bitmap.width, bitmap.height)
                    android.graphics.Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt(), true)
                } else {
                    bitmap
                }
                FileOutputStream(file).use { output ->
                    scaledBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 85, output)
                }
                if (scaledBitmap !== bitmap) scaledBitmap.recycle()
                bitmap.recycle()
            }
        } catch (e: Exception) {
            throw RuntimeException("Error saving image from URI", e)
        }
        return file
    }

    fun uploadImage(file: File, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Starting image upload for file: ${file.absolutePath}, size: ${file.length()} bytes")

        if (!file.exists()) {
            val error = "Image file does not exist: ${file.absolutePath}"
            Log.e(TAG, error)
            onError(error)
            return
        }

        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("image", file.name, requestBody)

        RetrofitClient.apiService.uploadImage(part).enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: retrofit2.Call<String>, response: retrofit2.Response<String>) {
                if (response.isSuccessful) {
                    response.body()?.let { imageUrl ->
                        Log.d(TAG, "Upload successful, received URL: $imageUrl")
                        onSuccess(imageUrl)
                    } ?: run {
                        val error = "Empty response from server"
                        Log.e(TAG, error)
                        onError(error)
                    }
                } else {
                    val error = "Upload failed: ${response.code()} - ${response.message()}"
                    Log.e(TAG, error)
                    onError(error)
                }
            }

            override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                val error = "Upload error: ${t.message}"
                Log.e(TAG, error, t)
                onError(error)
            }
        })
    }
}