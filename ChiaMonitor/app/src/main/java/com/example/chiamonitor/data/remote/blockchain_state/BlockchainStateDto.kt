package com.example.chiamonitor.data.remote.blockchain_state

import kotlinx.serialization.Serializable


@Serializable
data class BlockchainStateDto(
    val node_id: String,
    val height: Int,
    val block_max_cost: Long,
    val difficulty: Int,
    val space: Long,
    val syncing: Boolean,
    val sync_progress_height: Int,
    val sync_tip_height: Int,
)
