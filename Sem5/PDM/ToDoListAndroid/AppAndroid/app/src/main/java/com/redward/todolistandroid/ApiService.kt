package com.redward.todolistandroid

import retrofit2.http.*
import retrofit2.Call
import com.redward.todolistandroid.Task
import okhttp3.MultipartBody

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String, val ownerId: String)

interface ApiService {
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/tasks")
    fun getTasks(@Query("ownerId") ownerId: String, @Header("Authorization") token: String): Call<List<Task>>

    @PUT("/tasks/{id}")
    fun updateTask(@Path("id") taskId: String, @Body task: Task, @Header("Authorization") token: String): Call<Task>

    @POST("/tasks")
    fun addTask(@Body task: Task, @Header("Authorization") token: String): Call<Task>

    @Multipart
    @POST("/upload")
    fun uploadImage(@Part image: MultipartBody.Part): Call<String>
}

