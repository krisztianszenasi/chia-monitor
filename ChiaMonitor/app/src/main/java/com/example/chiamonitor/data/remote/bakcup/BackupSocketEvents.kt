package com.example.chiamonitor.data.remote.bakcup

import com.example.chiamonitor.data.remote.SocketManager
import com.example.chiamonitor.settings.ConnectionSettings
import io.socket.client.Socket
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.RuntimeException


fun initializeSocketForBackup(
    connectionSettings: ConnectionSettings,
    onProgress: (BackupProgress?) -> Unit,
    onFinished: () -> Unit,
): Socket? {
    return try {
        val socketManager = SocketManager(host = connectionSettings.host, apiKey = connectionSettings.apiKey)
        val socket = socketManager.backupSocket

        socket.connect()
        socket.on("progress") { args ->
            onProgress(socketResponseToBackupProgress(args))
        }
        socket.on("finished") {
            onFinished()
        }
        socket
    } catch (e: RuntimeException) {
        null
    }
}


private fun socketResponseToBackupProgress(args: Array<out Any>): BackupProgress? {
    return try {
        val progress = args[0].toString()
        Json.decodeFromString<BackupProgress>(progress)
    } catch (e: Exception) {
        null
    }
}