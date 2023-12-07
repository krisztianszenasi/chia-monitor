package com.example.chiamonitor.presentation.services

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.domain.ServiceReport


@Composable
fun ServicesList(
    servicesReport: List<ServiceReport>,
    onStarted: (ServiceReport) -> Unit,
    onStopped: (ServiceReport) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        servicesReport.forEach { report ->
            ServiceListItem(
                serviceReport = report,
                onStarted = onStarted,
                onStopped = onStopped,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}