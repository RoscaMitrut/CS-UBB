package com.redward.todolistandroid

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.ui.graphics.Color

@Composable
fun MyNotifications() {
    val context = LocalContext.current
    val channelId = "MyTestChannel"
    val notificationId = 0

    LaunchedEffect(Unit) {
        createNotificationChannel(channelId, context)
    }
        IconButton(
            onClick = {
                showSimpleNotificationWithTapAction(//showSimpleNotification(
                    context,
                    channelId,
                    notificationId,
                    "Notification",
                    "Notification text."
                )
            }) {
            Icon(
                imageVector = Icons.Default.Message,
                contentDescription = "Show Notifications",
                tint = Color.Gray,
                modifier = Modifier.size(32.dp)
            )
        }
}