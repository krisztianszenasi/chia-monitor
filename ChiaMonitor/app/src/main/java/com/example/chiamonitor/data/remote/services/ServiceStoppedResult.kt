package com.example.chiamonitor.data.remote.services

import kotlinx.serialization.Serializable

@Serializable
data class ServiceStoppedResult(
    val service: String,
    val success: Boolean,
)
