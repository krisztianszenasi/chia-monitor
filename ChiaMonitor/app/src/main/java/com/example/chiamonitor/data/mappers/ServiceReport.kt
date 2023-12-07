package com.example.chiamonitor.data.mappers

import com.example.chiamonitor.data.remote.services.ServiceReportDto
import com.example.chiamonitor.domain.ServiceReport
import com.example.chiamonitor.domain.ServiceState

fun ServiceReportDto.toServiceReport(): ServiceReport {
    return ServiceReport(
        name = name,
        state = if(running) ServiceState.RUNNING else ServiceState.STOPPED
    )
}