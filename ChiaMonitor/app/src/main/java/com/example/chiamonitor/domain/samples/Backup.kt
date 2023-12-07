package com.example.chiamonitor.domain.samples

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.chiamonitor.domain.Backup
import java.time.LocalDateTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
val backupSample = Backup(
    name = "vacuumed_blockchain_v2_mainnet.sqlite",
    height = 902345,
    size = 546_000_000,
    created = LocalDateTime.of(2023, 9, 9, 10, 30),
)