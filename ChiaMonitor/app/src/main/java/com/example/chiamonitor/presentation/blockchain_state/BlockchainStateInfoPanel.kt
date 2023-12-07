package com.example.chiamonitor.presentation.blockchain_state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.R
import com.example.chiamonitor.domain.BlockchainState
import com.example.chiamonitor.domain.samples.sampleSyncingBlockchainState
import com.example.chiamonitor.presentation.asString
import com.example.chiamonitor.presentation.shared.InfoCard
import com.example.chiamonitor.ui.theme.ChiaMonitorTheme


@Composable
fun BlockchainStateInfoPanel(blockchainState: BlockchainState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            InfoCard(
                icon = R.drawable.baseline_height_24_light,
                title = stringResource(R.string.height),
                text = "%,d".format(blockchainState.height),
                modifier = Modifier.padding(4.dp)
            )
            InfoCard(
                icon = R.drawable.baseline_sd_storage_24_light,
                title = stringResource(R.string.max_cost),
                text = "${blockchainState.blockMaxCostKiB.asString()} KiB",
                modifier = Modifier.padding(4.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            InfoCard(
                icon = R.drawable.baseline_fitness_center_24_light,
                title = stringResource(R.string.difficulty),
                text = blockchainState.difficulty.toString(),
                modifier = Modifier.padding(4.dp)
            )
            InfoCard(
                icon = R.drawable.baseline_public_light,
                title = stringResource(R.string.netspace),
                text = "${blockchainState.spaceEib.asString()} EiB",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BlockchainStateDataCardPreview() {
    ChiaMonitorTheme {
        BlockchainStateInfoPanel(sampleSyncingBlockchainState)
    }
}