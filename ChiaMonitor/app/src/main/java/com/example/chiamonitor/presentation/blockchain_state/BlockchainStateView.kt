package com.example.chiamonitor.presentation.blockchain_state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chiamonitor.R
import com.example.chiamonitor.domain.BlockchainState
import com.example.chiamonitor.domain.samples.sampleSyncingBlockchainState
import com.example.chiamonitor.presentation.shared.MinimalAddress
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme


@Composable
fun BlockchainStateView(blockchainState: BlockchainState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        NodeIdTitle(
            nodeId = blockchainState.nodeId,
            modifier = Modifier.padding(10.dp)
        )
        BlockchainStateInfoPanel(
            blockchainState = blockchainState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        SyncCircularProgressIndicator(
            blockchainState = blockchainState,
            size = 250.dp,
        )
    }
}

@Composable
fun NodeIdTitle(nodeId: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.nodeid),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.size(4.dp))
        MinimalAddress(
            address = nodeId,
            fontSize = 25.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BlockchainStateViewPreview() {
    ChiaMonitorTheme {
        BlockchainStateView(
            blockchainState = sampleSyncingBlockchainState,
            modifier = Modifier.fillMaxSize()
        )
    }
}