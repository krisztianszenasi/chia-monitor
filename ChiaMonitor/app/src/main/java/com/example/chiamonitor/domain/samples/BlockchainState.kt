package com.example.chiamonitor.domain.samples

import com.example.chiamonitor.domain.BlockchainState


val sampleSyncingBlockchainState =  BlockchainState(
    nodeId = "b3d9de85d29931c10050b56c7afb91c99141943fc81ff2d1a8425e52be0d08ab",
    height = 3_326_213,
    blockMaxCost = 11000000000,
    difficulty = 1976,
    space = 251933217250036449,
    syncing = true,
    syncProgressHeight = 2_123_456,
    syncTipHeight = 3_326_213,
)


val sampleSyncedBlockchainState = BlockchainState(
    nodeId = "b3d9de85d29931c10050b56c7afb91c99141943fc81ff2d1a8425e52be0d08ab",
    height = 4_326_213,
    blockMaxCost = 11000000000,
    difficulty = 1976,
    space = 251933217250036449,
    syncing = false,
    syncProgressHeight = 0,
    syncTipHeight = 0,
)
