package com.example.chiamonitor.data.remote.services

import kotlinx.serialization.Serializable


@Serializable
data class ServiceStartedResult(
    val error: String?,
    val service: String,
    val success: Boolean,
)
