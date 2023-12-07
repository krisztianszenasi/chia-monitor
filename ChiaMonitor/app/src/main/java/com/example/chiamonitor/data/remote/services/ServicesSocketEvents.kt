package com.example.chiamonitor.data.remote.services


import com.example.chiamonitor.data.remote.SocketManager
import com.example.chiamonitor.settings.ConnectionSettings
import io.socket.client.Socket
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun initializeSocketForServices(
    connectionSettings: ConnectionSettings,
    onStarted: (ServiceStartedResult?) -> Unit,
    onStopped: (ServiceStoppedResult?) -> Unit
): Socket? {
    return try {
        val socketManager = SocketManager(host = connectionSettings.host, apiKey = connectionSettings.apiKey)
        val socket = socketManager.servicesSocket
        socket.connect()

        socket.on("started") { args ->
            onStarted(parseArgsToServiceStartResult(args))
        }

        socket.on("stopped") {args ->
            onStopped(parseArgsToServiceStopResult(args))
        }

        socket
    } catch (e: RuntimeException) {
        null
    }
}


private fun parseArgsToServiceStartResult(args: Array<out Any>): ServiceStartedResult? {
    return try {
        val state = args[0].toString()
        Json.decodeFromString<ServiceStartedResult>(state)
    } catch (e: Exception) {
        null
    }
}

private fun parseArgsToServiceStopResult(args: Array<out Any>): ServiceStoppedResult? {
    return try {
        val state = args[0].toString()
        Json.decodeFromString<ServiceStoppedResult>(state)
    } catch (e: Exception) {
        null
    }
}