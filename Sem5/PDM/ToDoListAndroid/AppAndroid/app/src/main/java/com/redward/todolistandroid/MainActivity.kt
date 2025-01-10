package com.redward.todolistandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Add
import androidx.navigation.compose.rememberNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val viewModel: TaskViewModel = viewModel()
                val myJobsViewModel: MyJobsViewModel = viewModel(
                    factory = MyJobsViewModel.Factory(application)
                )
                MainScreen(viewModel, myJobsViewModel)
            }
        }
        val reminderWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
            .build()
        WorkManager.getInstance(this).enqueue(reminderWorkRequest)
    }
}
@Composable
fun MainScreen(viewModel: TaskViewModel, myJobsViewModel: MyJobsViewModel) {
    var isLoggedIn by remember { mutableStateOf(false) }
    val tasks by viewModel.tasks.observeAsState(emptyList())
    var errorMessage by remember { mutableStateOf("") }
    val navController = rememberNavController()

    LaunchedEffect(viewModel.token) {
        isLoggedIn = viewModel.token != null
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoggedIn) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MyNetworkStatus()
                    IconButton(onClick = {
                        viewModel.logout()
                        isLoggedIn = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.Red,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    MyNotifications()
                }
                //MyJobs(myJobsViewModel)
                TaskApp(tasks, viewModel)
            }
        } else {
            LoginScreen(viewModel = viewModel) {
                isLoggedIn = true
                viewModel.fetchTasks { fetchedTasks ->
                    // No need to update tasks here, LiveData will handle it
                }
            }
        }
        if (errorMessage.isNotEmpty()) {
            // Handle and display error messages
            println("Error: $errorMessage")
        }
    }
}