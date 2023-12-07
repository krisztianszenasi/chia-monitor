package com.example.chiamonitor.domain

import kotlin.math.pow

data class BlockchainState(
    val nodeId: String,
    val height: Int,
    val blockMaxCost: Long,
    val difficulty: Int,
    val space: Long,
    val syncing: Boolean,
    val syncProgressHeight: Int,
    val syncTipHeight: Int,
) {
    val progress: Float
        get() {
            if(syncing) {
                return syncProgressHeight.toFloat().div(syncTipHeight)
            }
            return 1f
        }

    val progressAsPercentage: Float
        get() {
            return progress * 100
        }

    val blocksBehind: Int
        get() {
            return syncTipHeight - syncProgressHeight
        }

    val spaceEib: Float
        get() {
            return space.div(1000f.pow(6))
        }

    val blockMaxCostKiB: Float
        get(){
            return blockMaxCost.div(12_000).div(1024).toFloat()
        }
}
