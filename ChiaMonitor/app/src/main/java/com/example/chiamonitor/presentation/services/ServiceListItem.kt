package com.example.chiamonitor.presentation.services

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.domain.ServiceReport
import com.example.chiamonitor.domain.ServiceState
import com.example.chiamonitor.presentation.shared.ChiaListElement
import com.example.chiamonitor.presentation.shared.ChiaServiceIcon
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme

@Composable
fun ServiceListItem(
    serviceReport: ServiceReport,
    onStarted: (ServiceReport) -> Unit,
    onStopped: (ServiceReport) -> Unit,
    modifier: Modifier = Modifier,
) {

    ChiaListElement(
        icon = {ChiaServiceIcon(size = 80.dp, service = serviceReport.name)},
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Text(serviceReport.name.replace('_', ' '))
            when(serviceReport.state) {
                ServiceState.IN_BETWEEN -> CircularProgressIndicator()
                ServiceState.STOPPED -> IconButton(onClick = { onStarted(serviceReport) }) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        modifier = Modifier.size(70.dp),
                        contentDescription = "start button",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                ServiceState.RUNNING -> IconButton(onClick = { onStopped(serviceReport) }) {
                    Icon(
                        imageVector = Icons.Filled.Stop,
                        modifier = Modifier.size(70.dp),
                        contentDescription = "stop button",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun ServiceListItemPreview() {
    ChiaMonitorTheme {
        ServiceListItem(
            serviceReport = ServiceReport(
                    name = "chia_full_node",
                    state = ServiceState.STOPPED
                ),
            onStarted = {},
            onStopped = {}
        )
    }
}
