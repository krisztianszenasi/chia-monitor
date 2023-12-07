package com.example.chiamonitor.presentation.services

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chiamonitor.R
import com.example.chiamonitor.data.mappers.toServiceReport
import com.example.chiamonitor.data.remote.ChiaRestApi
import com.example.chiamonitor.data.remote.bakcup.BackupResponse
import com.example.chiamonitor.data.remote.services.ServicesReportDto
import com.example.chiamonitor.domain.ServiceReport

@Composable
fun ServicesScreen(
    chiaRestApi: ChiaRestApi,
    servicesReport: List<ServiceReport>,
    onStartService: (ServiceReport) -> Unit,
    onStopService: (ServiceReport) -> Unit,
    onInitialFetched: (List<ServiceReport>) -> Unit,
    onError: (Int) -> Unit,
) {
    LaunchedEffect(Unit) {
        val reportDto = getServicesReport(chiaRestApi, onError = onError)
        if(reportDto != null) {
            val serviceReports = reportDto.services.map {
                it.toServiceReport()
            }
            onInitialFetched(serviceReports)
        } else {
            onInitialFetched(emptyList())
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(servicesReport.isEmpty()) {
            CircularProgressIndicator()
        } else{
            ServicesList(
                servicesReport = servicesReport,
                onStarted = onStartService,
                onStopped = onStopService,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}


private suspend fun getServicesReport(
    chiaRestApi: ChiaRestApi,
    onError: (Int) -> Unit,
): ServicesReportDto? {
    try {
        val response = chiaRestApi.getServiceReport()

        return if (response.isSuccessful) {
            response.body()
        } else {
            when(response.code()) {
                401 -> onError(R.string.http_no_api_key)
                403 -> onError(R.string.http_invalid_api_key)
                else -> onError(R.string.http_wrong_host)
            }
            null
        }
    } catch (e: Exception) {
        onError(R.string.http_wrong_host)
        return null
    }
}