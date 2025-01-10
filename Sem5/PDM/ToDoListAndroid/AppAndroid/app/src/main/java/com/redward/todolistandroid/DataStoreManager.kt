// DataStoreManager.kt
package com.redward.todolistandroid

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.util.Log

class DataStoreManager(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")

    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val PASSWORD_KEY = stringPreferencesKey("password")
        private const val TAG = "DataStoreManager"
    }

    val username: Flow<String?> = context.dataStore.data.map { preferences ->
        val username = preferences[USERNAME_KEY]
        Log.d(TAG, "Accessed username: $username")
        username    }

    val password: Flow<String?> = context.dataStore.data.map { preferences ->
        val password = preferences[PASSWORD_KEY]
        Log.d(TAG, "Accessed password: $password")
        password    }

    suspend fun saveCredentials(username: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
            preferences[PASSWORD_KEY] = password
            Log.d(TAG, "Stored username: $username")
            Log.d(TAG, "Stored password: $password")
        }
    }

    suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences.clear()
            Log.d(TAG, "Cleared all credentials")
        }
    }
}