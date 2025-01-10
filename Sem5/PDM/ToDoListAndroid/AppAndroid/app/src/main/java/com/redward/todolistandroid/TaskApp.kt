package com.redward.todolistandroid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.redward.todolistandroid.Task
import com.redward.todolistandroid.TaskDetailScreen
import com.redward.todolistandroid.TaskListScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun TaskApp(tasks: List<Task>, viewModel: TaskViewModel) {
    val navController = rememberNavController()
    var currentScreen by remember { mutableStateOf("taskList") }

    NavHost(navController, startDestination = "taskList") {
        composable("taskList") {
            AnimatedVisibility(
                visible = currentScreen == "taskList",
                enter = slideInHorizontally(initialOffsetX = { -1000 }),
                exit = slideOutHorizontally(targetOffsetX = { 1000 })
            ) {
                TaskListScreen(
                    tasks = tasks,
                    onTaskClick = { task ->
                        currentScreen = "taskDetail/${task.id}"
                        navController.navigate("taskDetail/${task.id}")
                    },
                    onAddTaskClick = {
                        currentScreen = "taskAdd"
                        navController.navigate("taskAdd")
                    }
                )
            }
        }
        composable(
            "taskDetail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val task = tasks.find { it.id == taskId } ?: return@composable
            AnimatedVisibility(
                visible = currentScreen == "taskDetail/${taskId}",
                enter = slideInHorizontally(initialOffsetX = { 1000 }),
                exit = slideOutHorizontally(targetOffsetX = { -1000 })
            ) {
                TaskDetailScreen(
                    task,
                    onEditClick = { updatedTask ->
                        viewModel.updateTask(updatedTask, onSuccess = {
                            currentScreen = "taskList"
                            navController.popBackStack()
                        }, onError = { error ->
                            // Handle error
                        })
                    },
                    onBackClick = {
                        currentScreen = "taskList"
                        navController.popBackStack()
                    }
                )
            }
        }
        composable("taskAdd") {
            AnimatedVisibility(
                visible = currentScreen == "taskAdd",
                enter = slideInHorizontally(initialOffsetX = { 1000 }),
                exit = slideOutHorizontally(targetOffsetX = { -1000 })
            ) {
                TaskAddScreen(
                    onAddClick = { newTask ->
                        viewModel.addTask(newTask, onSuccess = {
                            currentScreen = "taskList"
                            navController.popBackStack()
                        }, onError = { error ->
                            // Handle error
                        })
                    },
                    onBackClick = {
                        currentScreen = "taskList"
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}