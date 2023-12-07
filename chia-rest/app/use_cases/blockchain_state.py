"""Blockchain state related use cases."""

from app.entities.blockchain_state import BlockChainState
from app.entities.mappers.blockchain_state import \
    parse_rpc_response_to_blockchain_state
from app.rpc_requests.blockchain_state import send_rpc_get_blockchain_state


def get_blockchain_state() -> BlockChainState | None:
    """Get the current state of the blockchain as a `BlockChainState` entity.

    Returns None if the underlying rpc request fails.

    Returns:
        BlockChainState | None: state retrieved
    """
    if state_data := send_rpc_get_blockchain_state():
        return parse_rpc_response_to_blockchain_state(state_data)
    return None
