package com.example.chiamonitor.presentation.blockchain_state

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.chiamonitor.R
import com.example.chiamonitor.domain.BlockchainState
import com.example.chiamonitor.presentation.shared.LargeInfoTextScreen



@Composable
fun BlockchainStateScreen(
    state: BlockchainState?,
    nodeRunning: Boolean,
) {

    if(!nodeRunning){
        LargeInfoTextScreen(
            title = "The full node isn't running.",
            modifier = Modifier.fillMaxSize()
        )
    } else if (state != null){
        BlockchainStateView(
            blockchainState = state!!,
            modifier = Modifier.fillMaxSize(),
        )
    } else {
        LargeInfoTextScreen(
            title = stringResource(R.string.no_blockchain_message),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
