// ReminderWorker.kt
package com.redward.todolistandroid

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.util.Log

class ReminderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val pendingTasks = getPendingTasks()
        if (pendingTasks.isNotEmpty()) {
        }
        sendReminderNotification(pendingTasks)// MOVE THIS UP 2 LINES

        Log.d("ReminderWorker", "Notification sent for ${pendingTasks.size} pending tasks.")
        return Result.success()
    }

    private fun getPendingTasks(): List<Task> {
        // Logic to fetch pending tasks from the database or API
        // This is a placeholder implementation
        return listOf()
    }

    private fun sendReminderNotification(tasks: List<Task>) {
        val context = applicationContext
        val channelId = "ReminderChannel"
        createNotificationChannel(channelId, context)
        showSimpleNotification(
            context,
            channelId,
            1,
            "Pending Tasks Reminder",
            //"You have ${tasks.size} pending tasks."
            "Don't forget to do your pending tasks!"
        )
    }
}