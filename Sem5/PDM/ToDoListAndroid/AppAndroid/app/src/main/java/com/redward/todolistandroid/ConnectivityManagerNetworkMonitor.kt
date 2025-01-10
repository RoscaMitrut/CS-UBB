package com.redward.todolistandroid

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

@OptIn(ExperimentalCoroutinesApi::class)
class ConnectivityManagerNetworkMonitor(private val context: Context) {
    val isOnline: Flow<Boolean> = callbackFlow {
        val connectivityManager = getSystemService(context, ConnectivityManager::class.java)
        if (connectivityManager == null) {
            channel.trySend(false) // No connectivity manager, assume offline
            awaitClose {}
            return@callbackFlow
        }

        val callback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                channel.trySend(true)
            }

            override fun onLost(network: Network) {
                channel.trySend(false)
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        try {
            connectivityManager.registerNetworkCallback(networkRequest, callback)
        } catch (e: Exception) {
            channel.trySend(false) // Assume offline on exception
        }

        // Emit the current connection status
        channel.trySend(connectivityManager.isCurrentlyConnected())

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.conflate()

    @Suppress("DEPRECATION")
    private fun ConnectivityManager.isCurrentlyConnected(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activeNetwork?.let { network ->
                getNetworkCapabilities(network)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } ?: false
        } else {
            activeNetworkInfo?.isConnected ?: false
        }
    }
}
