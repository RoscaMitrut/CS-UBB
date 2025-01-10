package com.redward.todolistandroid

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun TaskAddScreen(onAddClick: (Task) -> Unit, onBackClick: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var isDone by remember { mutableStateOf(false) }

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
            val newTask = Task("", "", title, date, isDone)
            onAddClick(newTask)
        }) {
            Text("Add Task")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onBackClick() }) {
            Text("Back")
        }
    }
}