package com.example.chiamonitor.domain

import java.time.LocalDateTime

data class Backup(
    val name: String,
    val size: Long,
    val height: Int,
    val created: LocalDateTime,
)


data class BackupProgress(
    val progress: Double,
    val currentSize: Long,
    val goalSize: Long,
)