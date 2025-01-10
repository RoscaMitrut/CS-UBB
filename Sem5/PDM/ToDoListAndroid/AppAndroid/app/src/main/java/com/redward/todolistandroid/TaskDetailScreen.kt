package com.redward.todolistandroid

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.redward.todolistandroid.Task
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*

@Composable
fun TaskDetailScreen(task: Task, onEditClick: (Task) -> Unit, onBackClick: () -> Unit) {
    var title by remember { mutableStateOf(task.title) }
    var date by remember { mutableStateOf(task.date) }
    var isDone by remember { mutableStateOf(task.isDone) }

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
        Button(onClick = {
            val updatedTask = task.copy(title = title, date = date, isDone = isDone)
            onEditClick(updatedTask)
        }) {
            Text("Save Changes")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onBackClick() }) {
            Text("Back")
        }
    }
}