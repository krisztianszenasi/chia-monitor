package com.example.chiamonitor.data.remote.bakcup

import com.example.chiamonitor.domain.Backup
import kotlinx.serialization.Serializable

data class BackupDto(
    val name: String,
    val size: Long,
    val height: Int?,
    val created: String,
)


data class BackupResponse(
    val backup: BackupDto?,
    val in_progress: Boolean,
)


@Serializable
data class BackupProgress(
    val progress: Double,
    val current_size: Long,
    val goal_size: Long,
)
