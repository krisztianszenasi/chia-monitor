"""Block chain state related mappers."""


from app.entities.blockchain_state import BlockChainState


def parse_rpc_response_to_blockchain_state(rpc_response: dict) -> BlockChainState | None:
    try:
        return BlockChainState(
            node_id=rpc_response['node_id'],
            height=rpc_response['peak']['height'],
            block_max_cost=rpc_response['block_max_cost'],
            difficulty=rpc_response['difficulty'],
            space=rpc_response['space'],
            syncing=rpc_response['sync']['sync_mode'],
            sync_progress_height=rpc_response['sync']['sync_progress_height'],
            sync_tip_height=rpc_response['sync']['sync_tip_height'],
        )
    except (KeyError, TypeError):
        return None
