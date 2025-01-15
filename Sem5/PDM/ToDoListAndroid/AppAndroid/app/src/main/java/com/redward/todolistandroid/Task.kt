package com.redward.todolistandroid

data class Task(
    val id: String,
    val ownerId: String,
    val title: String,
    val date: String,
    val isDone: Boolean,
    val imageUrl: String? = null
)
