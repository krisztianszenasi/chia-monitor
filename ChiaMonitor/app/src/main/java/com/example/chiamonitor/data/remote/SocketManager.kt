package com.example.chiamonitor.data.remote

import io.socket.client.IO
import io.socket.client.Socket

class SocketManager constructor(
    private var host: String,
    private var apiKey: String
) {
    val blockchainStateSocket: Socket by lazy {
        createSocket("blockchain-state")
    }

    val backupSocket: Socket by lazy {
        createSocket("backup")
    }

    val servicesSocket: Socket by lazy {
        createSocket("services")
    }

    private fun createSocket(nameSpace: String): Socket {
        return IO.socket(
            "$host/$nameSpace?api_key=$apiKey",
            IO.Options().apply {
                reconnection = false
            }
        )
    }
}