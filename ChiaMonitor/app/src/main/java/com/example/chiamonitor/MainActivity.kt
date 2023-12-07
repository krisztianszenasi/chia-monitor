package com.example.chiamonitor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.datastore.dataStore
import com.example.chiamonitor.presentation.MainScreen
import com.example.chiamonitor.settings.ConnectionSettings
import com.example.chiamonitor.settings.ConnectionSettingsSerializer
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore by dataStore("connection-settings.json", ConnectionSettingsSerializer)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var connectionSettings by remember { mutableStateOf(ConnectionSettings()) }
            var loadingState by remember { mutableStateOf(LoadingState.LOADING) }
            val scope = rememberCoroutineScope()

            var errorMessage by remember { mutableStateOf<Int?>(null) }
            var connectSuccessfully by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                try {
                    val data = dataStore.data.first()
                    connectionSettings = data
                    loadingState = LoadingState.LOADED
                } catch (e: Exception) {
                    Log.e("DATA_STORE", "Error loading data: ${e.message}")
                }
            }

            ChiaMonitorTheme {

                if(errorMessage != null) {
                    displayErrorToast(LocalContext.current, stringResource(errorMessage!!))
                    errorMessage = null
                }

                when(loadingState) {
                    LoadingState.LOADING -> {
                        CircularProgressIndicator()
                    }
                    LoadingState.LOADED -> {
                        MainScreen(
                            connectionSettings = connectionSettings,
                            onConnect = { connectSuccessfully = true },
                            onDisconnect = {
                               if(!connectSuccessfully) {
                                    errorMessage = R.string.wrong_api_key_message
                               }
                                connectSuccessfully = false
                            },
                            onError = { msg -> errorMessage = msg },
                            onNewSettings = {   newSettings ->
                                connectionSettings = newSettings
                                scope.launch {
                                    updateSettings(newSettings)
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private suspend fun updateSettings(settings: ConnectionSettings) {
        dataStore.updateData {
            it.copy(host = settings.host, apiKey = settings.apiKey)
        }
    }

    private fun displayErrorToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}

enum class LoadingState {
    LOADING,
    LOADED
}
