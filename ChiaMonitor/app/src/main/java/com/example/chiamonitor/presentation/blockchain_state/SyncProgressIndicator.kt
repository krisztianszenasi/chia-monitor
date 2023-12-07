package com.example.chiamonitor.presentation.blockchain_state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.R
import com.example.chiamonitor.domain.BlockchainState
import com.example.chiamonitor.domain.samples.sampleSyncedBlockchainState
import com.example.chiamonitor.domain.samples.sampleSyncingBlockchainState
import com.example.chiamonitor.presentation.asString
import com.example.chiamonitor.presentation.dpToSp
import com.example.chiamonitor.presentation.shared.ActiveCircularProgressIndicator
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme


@Composable
fun SyncCircularProgressIndicator(
    blockchainState: BlockchainState,
    size: Dp = 100.dp,
    modifier: Modifier = Modifier,
) {
    val percentageText = "${blockchainState.progressAsPercentage.asString()}%"
    val blockBehindText =
        stringResource(R.string.behind, "%,d".format(blockchainState.blocksBehind))


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size),
    )
    {
        ActiveCircularProgressIndicator(
            progress = blockchainState.progress,
            strokeWidth = size / 8,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = percentageText,
                fontWeight = FontWeight.Bold,
                fontSize = dpToSp(dp = size / 6),
            )
            Text(
                text = if(blockchainState.syncing) blockBehindText else stringResource(R.string.synced),
                fontStyle = FontStyle.Italic,
                fontSize = dpToSp(dp = size / 12),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SyncCircularProgressIndicatorSyncingPreview() {
    ChiaMonitorTheme {
        SyncCircularProgressIndicator(
            blockchainState = sampleSyncingBlockchainState,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SyncCircularProgressIndicatorSyncedPreview() {
    ChiaMonitorTheme {
        SyncCircularProgressIndicator(
            blockchainState = sampleSyncedBlockchainState,
        )
    }
}
