// TaskViewModel.kt
package com.redward.todolistandroid

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File


class TaskViewModel(application: Application) : AndroidViewModel(application) {
    val dataStoreManager = DataStoreManager(application)
    var token: String? = null
    var ownerId: String? = null
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks
    var loginError: String? = null
    var fetchError: String? = null

    init {
        viewModelScope.launch {
            val username = dataStoreManager.username.first()
            val password = dataStoreManager.password.first()
            if (username != null && password != null) {
                login(username, password, onSuccess = {
                    fetchTasks { }
                }, onError = {})
            }
        }
    }

    fun login(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val loginRequest = LoginRequest(username, password)
        RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        token = "Bearer ${loginResponse.token}"
                        ownerId = loginResponse.ownerId
                        loginError = null
                        viewModelScope.launch {
                            dataStoreManager.saveCredentials(username, password)
                        }
                        onSuccess()
                    } else {
                        loginError = "Login failed: Response is null"
                        onError(loginError ?: "Unknown error")
                    }
                } else {
                    loginError = "Login failed: ${response.code()} - ${response.message()}"
                    onError(loginError ?: "Unknown error")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginError = "Network error: ${t.localizedMessage ?: t.message ?: "Unknown error"}"
                onError(loginError ?: "Unknown error")
            }
        })
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearCredentials()
            token = null
            ownerId = null
            _tasks.value = emptyList() // Clear tasks
        }
    }

    fun fetchTasks(onTasksFetched: (List<Task>) -> Unit) {
        token?.let { authToken ->
            ownerId?.let { userId ->
                RetrofitClient.apiService.getTasks(userId, authToken).enqueue(object : Callback<List<Task>> {
                    override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                        if (response.isSuccessful) {
                            _tasks.value = response.body()
                            onTasksFetched(_tasks.value ?: emptyList())
                        } else {
                            fetchError = "Failed to fetch tasks"
                        }
                    }

                    override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                        fetchError = "Fetch error: ${t.message}"
                    }
                })
            }
        }
    }

    fun addTask(task: Task, onSuccess: () -> Unit, onError: (String) -> Unit) {
        token?.let { authToken ->
            ownerId?.let { userId ->
                val taskWithOwner = task.copy(ownerId = userId)
                RetrofitClient.apiService.addTask(taskWithOwner, authToken).enqueue(object : Callback<Task> {
                        override fun onResponse(call: Call<Task>, response: Response<Task>) {
                            if (response.isSuccessful) {
                                val newTask = response.body()
                                newTask?.let {
                                    _tasks.value = _tasks.value?.plus(newTask)
                                }
                                onSuccess()
                            } else {
                                onError("Failed to add task: ${response.code()} - ${response.message()}")
                            }
                        }
                        override fun onFailure(call: Call<Task>, t: Throwable) {
                            onError("Network error: ${t.localizedMessage ?: t.message ?: "Unknown error"}")
                        }
                })
            }
        }
    }

    fun addTask2(task: Task, onSuccess: () -> Unit, onError: (String) -> Unit) {
        token?.let { authToken ->
            ownerId?.let { userId ->
                val taskWithOwner = task.copy(ownerId = userId)
                RetrofitClient.apiService.addTask(taskWithOwner, authToken).enqueue(object : Callback<Task> {
                    override fun onResponse(call: Call<Task>, response: Response<Task>) {
                        if (response.isSuccessful) {
                            fetchTasks {
                                onSuccess()
                            }
                        } else {
                            onError("Failed to add task: ${response.code()} - ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<Task>, t: Throwable) {
                        onError("Network error: ${t.localizedMessage ?: t.message ?: "Unknown error"}")
                    }
                })
            }
        }
    }

    fun updateTask(task: Task, onSuccess: () -> Unit, onError: (String) -> Unit) {
        token?.let { authToken ->
            RetrofitClient.apiService.updateTask(task.id, task, authToken).enqueue(object : Callback<Task> {
                override fun onResponse(call: Call<Task>, response: Response<Task>) {
                    if (response.isSuccessful) {
                        val updatedTask = response.body()
                        updatedTask?.let {
                            _tasks.value = _tasks.value?.map { if (it.id == updatedTask.id) updatedTask else it }
                        }
                        onSuccess()
                    } else {
                        onError("Failed to update task: ${response.code()} - ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<Task>, t: Throwable) {
                    onError("Network error: ${t.localizedMessage ?: t.message ?: "Unknown error"}")
                }
            })
        }
    }

    fun uploadTaskImage(file: File, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        Log.d("TaskViewModel", "Starting task image upload")
        CameraUtils.uploadImage(
            file = file,
            onSuccess = { imageUrl ->
                Log.d("TaskViewModel", "Task image upload successful: $imageUrl")
                onSuccess(imageUrl)
            },
            onError = { error ->
                Log.e("TaskViewModel", "Task image upload failed: $error")
                onError(error)
            }
        )
    }
}