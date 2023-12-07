package com.example.chiamonitor.data.remote.services

import kotlinx.serialization.Serializable

@Serializable
data class ServiceReportDto(
    val name: String,
    val running: Boolean,
)

data class ServicesReportDto(
    val services: List<ServiceReportDto>
)
