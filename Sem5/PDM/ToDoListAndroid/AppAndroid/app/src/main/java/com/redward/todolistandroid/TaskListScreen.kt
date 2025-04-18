package com.redward.todolistandroid

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redward.todolistandroid.Task
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.Card
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun TaskListScreen(tasks: List<Task>, onTaskClick: (Task) -> Unit, onAddTaskClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(tasks) { task ->
                TaskItem(task, onClick = { onTaskClick(task) })
            }
        }
        FloatingActionButton(
            onClick = onAddTaskClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Task")
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = task.title, style = MaterialTheme.typography.h6)
                Text(text = "Due: ${task.date}", style = MaterialTheme.typography.body2)
                task.imageUrl?.let { imageUrl ->
                    Log.d("TaskListScreen", "Loading task image: $imageUrl")

                    val painter = rememberAsyncImagePainter(model = imageUrl)
                    val isLoading = painter.state is AsyncImagePainter.State.Loading

                    Box {
                        Image(
                            painter = painter,
                            contentDescription = "Task Image",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp)
                        )
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(24.dp) // Adjusted size for better fit
                            )
                        }
                    }
                }
            }
            if (task.isDone) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Done",
                    tint = Color.Green
                )
            }
        }
    }
}