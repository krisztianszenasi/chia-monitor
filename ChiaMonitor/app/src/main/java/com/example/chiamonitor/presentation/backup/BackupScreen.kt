package com.example.chiamonitor.presentation.backup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.data.remote.ChiaRestApi
import com.example.chiamonitor.data.remote.bakcup.BackupProgress
import com.example.chiamonitor.settings.ConnectionSettings

@Composable
fun BackupScreen(
    chiaRestApi: ChiaRestApi?,
    backupProgress: BackupProgress?,
    onError: (Int) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {

        if(backupProgress != null) {
            BackupInProgressView(backupProgress = backupProgress!!)
        } else {
            BackupFileView(
                chiaRestApi = chiaRestApi,
                onError = onError,
            )
        }
    }
}

