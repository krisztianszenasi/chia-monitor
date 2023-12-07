package com.example.chiamonitor.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chiamonitor.R
import com.example.chiamonitor.data.remote.ApiKeyInterceptor
import com.example.chiamonitor.data.remote.ChiaRestApi
import com.example.chiamonitor.data.remote.bakcup.BackupProgress
import com.example.chiamonitor.data.remote.bakcup.initializeSocketForBackup
import com.example.chiamonitor.data.remote.blockchain_state.initializeSocketForBlockchainState
import com.example.chiamonitor.data.remote.services.initializeSocketForServices
import com.example.chiamonitor.domain.BlockchainState
import com.example.chiamonitor.domain.ServiceReport
import com.example.chiamonitor.domain.ServiceState
import com.example.chiamonitor.presentation.backup.BackupScreen
import com.example.chiamonitor.presentation.blockchain_state.BlockchainStateScreen
import com.example.chiamonitor.presentation.navigation.BottomNavItem
import com.example.chiamonitor.presentation.navigation.BottomNavigation
import com.example.chiamonitor.presentation.services.ServicesScreen
import com.example.chiamonitor.presentation.settings.ConnectionSettingsScreen
import com.example.chiamonitor.settings.ConnectionSettings
import io.socket.client.Socket
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.IllegalArgumentException


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    connectionSettings: ConnectionSettings,
    onConnect: () -> Unit,
    onDisconnect: () -> Unit,
    onError: (Int) -> Unit,
    onNewSettings: (ConnectionSettings) -> Unit,
) {
    val navController = rememberNavController()

    var sockets by remember { mutableStateOf<Sockets?>(null) }
    var chiaRestApi by remember { mutableStateOf<ChiaRestApi?>(null) }
    var appState by remember { mutableStateOf(AppState()) }

    DisposableEffect(connectionSettings) {
        sockets = Sockets(
            blockchainStateSocket = initializeSocketForBlockchainState(
                connectionSettings = connectionSettings,
                onConnect = { onConnect() },
                onDisconnect = { onDisconnect() },
                onCurrent = { newState ->
                    appState = appState.copy(blockchainState = newState)
                },
                onError = { onError(R.string.socket_connection_error_message) }
            ),
            backupSocketSocket = initializeSocketForBackup(
                connectionSettings = connectionSettings,
                onProgress = { backupProgress ->
                    appState = appState.copy(backupProgress = backupProgress)
                 },
                onFinished = {
                    appState = appState.copy(backupProgress = null)
                }
            ),
            servicesSocket = initializeSocketForServices(
                connectionSettings = connectionSettings,
                onStarted = { serviceStartedResult ->
                    appState = appState.copy( serviceReports = appState.serviceReports.map{
                        if(serviceStartedResult?.service == it.name) {
                            it.copy(state = ServiceState.RUNNING)
                        } else{
                            it
                        }
                    })
                },
                onStopped = {   serviceStoppedResult ->
                    appState = appState.copy( serviceReports = appState.serviceReports.map{
                        if(serviceStoppedResult?.service == it.name) {
                            it.copy(state = ServiceState.STOPPED)
                        } else {
                            it
                        }
                    })
                }
            )
        )

        chiaRestApi = buildChiaRestApi(connectionSettings)

        onDispose {
            sockets?.blockchainStateSocket?.disconnect()
            sockets?.backupSocketSocket?.disconnect()
            sockets?.servicesSocket?.disconnect()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = { BottomNavigation(navController = navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.ConnectionSettings.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(BottomNavItem.BlockchainState.route) {
                    BlockchainStateScreen(
                        state = appState.blockchainState,
                        nodeRunning = isNodeRunning(appState)
                    )
                }

                composable(BottomNavItem.BackupScreen.route) {
                    BackupScreen(
                        chiaRestApi = chiaRestApi!!,
                        backupProgress = appState.backupProgress,
                        onError = onError,
                    )
                }
                composable(BottomNavItem.ServicesScreen.route) {
                    ServicesScreen(
                        chiaRestApi = chiaRestApi!!,
                        servicesReport = appState.serviceReports,
                        onStartService = { serviceReport ->
                            appState = appState.copy(
                                serviceReports = appState.serviceReports.map {
                                    if(serviceReport == it) {
                                        serviceReport.copy(state = ServiceState.IN_BETWEEN)
                                    } else {
                                        it
                                    }
                                }
                            )
                            sockets?.servicesSocket?.emit("start", serviceReport.name)
                        },
                        onStopService = {serviceReport ->
                            appState = appState.copy(
                                serviceReports = appState.serviceReports.map {
                                    if(serviceReport == it) {
                                        serviceReport.copy(state = ServiceState.IN_BETWEEN)
                                    } else {
                                        it
                                    }
                                }
                            )
                            sockets?.servicesSocket?.emit("stop", serviceReport.name)
                        },
                        onInitialFetched = { serviceReports ->
                            appState = appState.copy(serviceReports = serviceReports)
                        }
                    )
                }

                composable(BottomNavItem.ConnectionSettings.route) {
                    ConnectionSettingsScreen(
                        settings = connectionSettings,
                        onSave = {  newSettings ->
                           onNewSettings(newSettings)
                        }
                    )
                }
            }
        }
    }
}


data class Sockets(
    val blockchainStateSocket: Socket? = null,
    val backupSocketSocket: Socket? = null,
    val servicesSocket: Socket? = null,
)

data class AppState(
    val blockchainState: BlockchainState? = null,
    val backupProgress: BackupProgress? = null,
    val serviceReports: List<ServiceReport> = emptyList()
)


private fun isNodeRunning(appState: AppState): Boolean {
    return if(appState.serviceReports.isNotEmpty())
        appState.serviceReports.first {
            it.name == "chia_full_node"
        }.state == ServiceState.RUNNING
    else
        true
}


private fun buildChiaRestApi(settings: ConnectionSettings): ChiaRestApi? {
    return try {
        Retrofit.Builder()
            .baseUrl(settings.host)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ApiKeyInterceptor(settings.apiKey))
                    .build()
            )
            .build()
            .create(ChiaRestApi::class.java)
    } catch(e: IllegalArgumentException) {
        null
    }
}