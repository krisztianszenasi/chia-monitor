package com.example.chiamonitor.domain

enum class ServiceState {
    RUNNING,
    IN_BETWEEN,
    STOPPED
}

data class ServiceReport(
    val name: String,
    val state: ServiceState
)

