package com.example.chiamonitor.presentation.backup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.data.remote.bakcup.BackupProgress

@Composable
fun BackupInProgressView(backupProgress: BackupProgress) {
    ElevatedCard(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column(
            modifier = Modifier.padding(40.dp)
        ) {
            Text("Backing up . . .")
            LinearProgressIndicator(progress = backupProgress.progress.toFloat())
            Row {
                Text(sizesToText(backupProgress.current_size, backupProgress.goal_size))
            }
        }
    }
}

private fun sizesToText(currentSizeByte: Long, goalSizeByte: Long): String {
    return "${currentSizeByte / 1_000_000} / ${goalSizeByte / 1_000_000} MiB"
}