package com.example.chiamonitor.data.remote.blockchain_state

import com.example.chiamonitor.data.mappers.toBlockchainState
import com.example.chiamonitor.data.remote.SocketManager
import com.example.chiamonitor.domain.BlockchainState
import com.example.chiamonitor.settings.ConnectionSettings
import io.socket.client.Socket
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.RuntimeException


fun initializeSocketForBlockchainState(
    connectionSettings: ConnectionSettings,
    onConnect: () -> Unit,
    onDisconnect: () -> Unit,
    onCurrent: (BlockchainState?) -> Unit,
    onError: () -> Unit,
): Socket? {
    try {
        val socketManager = SocketManager(host = connectionSettings.host, apiKey = connectionSettings.apiKey)
        val socket = socketManager.blockchainStateSocket

        socket.connect()

        socket.on(Socket.EVENT_CONNECT) {
            onConnect()
        }
        socket.on("current") { args ->
            onCurrent(socketResponseToBlockChainState(args))
        }
        socket.on(Socket.EVENT_CONNECT_ERROR) {
            onError()
        }
        socket.on(Socket.EVENT_DISCONNECT) {
            onDisconnect()
        }

        return socket
    } catch (e: RuntimeException) {
        onError()
        return null
    }
}


private fun socketResponseToBlockChainState(args: Array<out Any>): BlockchainState? {
    return try {
        val state = args[0].toString()
        Json.decodeFromString<BlockchainStateDto>(state).toBlockchainState()
    } catch (e: Exception) {
        null
    }
}