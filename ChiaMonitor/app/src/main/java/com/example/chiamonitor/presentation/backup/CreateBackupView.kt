package com.example.chiamonitor.presentation.backup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.R
import com.example.chiamonitor.presentation.shared.ChiaButton

@Composable
fun CreateBackupView(onCreate: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("You have no backups yet.")
        Spacer(Modifier.height(20.dp))
        ChiaButton(
            text = stringResource(R.string.create_backup),
            onClick = { onCreate() }
        )
    }
}


