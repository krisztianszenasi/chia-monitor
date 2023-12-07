package com.example.chiamonitor.data.mappers

import com.example.chiamonitor.data.remote.blockchain_state.BlockchainStateDto
import com.example.chiamonitor.domain.BlockchainState


fun BlockchainStateDto.toBlockchainState(): BlockchainState {
    return BlockchainState(
        nodeId = node_id,
        height = height,
        blockMaxCost = block_max_cost,
        difficulty = difficulty,
        space = space,
        syncing = syncing,
        syncProgressHeight = sync_progress_height,
        syncTipHeight = sync_tip_height,
    )
}