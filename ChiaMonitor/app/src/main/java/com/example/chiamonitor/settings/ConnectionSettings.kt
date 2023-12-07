package com.example.chiamonitor.settings

import kotlinx.serialization.Serializable


@Serializable
data class ConnectionSettings(
    val host: String = "",
    val apiKey: String = "",
)
