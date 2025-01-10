package com.redward.todolistandroid

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.*
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel


data class MyJobUiState(val isRunning: Boolean = false, val progress: Int = 0, val result: Int = 0)

class MyJobsViewModel(application: Application) : AndroidViewModel(application) {
    var uiState by mutableStateOf(MyJobUiState())
        private set
    private var workManager: WorkManager = WorkManager.getInstance(application)
    private var workId: UUID? = null

    init {
        startJob()
    }

    private fun startJob() {
        viewModelScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val inputData = Data.Builder()
                .putInt("to", 10)
                .build()
            val myWork = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build()
            workId = myWork.id
            uiState = uiState.copy(isRunning = true)
            workManager.apply {
                enqueue(myWork)
                getWorkInfoByIdLiveData(workId!!).asFlow().collect {
                    //Log.d("MyJobsViewModel", "$it")
                    uiState = uiState.copy(
                        isRunning = !it.state.isFinished,
                        progress = it.progress.getInt("progress", 0),
                    )
                    if (it.state.isFinished) {
                        uiState = uiState.copy(
                            result = it.outputData.getInt("result", 0),
                        )
                    }
                }
            }
        }
    }

    fun cancelJob() {
        workManager.cancelWorkById(workId!!)
    }

    companion object {
        fun Factory(application: Application): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MyJobsViewModel(application)
            }
        }
    }
}

@Composable
fun MyJobs(myJobsViewModel: MyJobsViewModel) {
        Text(
            "${myJobsViewModel.uiState}",
            style = MaterialTheme.typography.h4,
        )
        Button(onClick = { myJobsViewModel.cancelJob() }) {
            Text("Cancel")
        }
}