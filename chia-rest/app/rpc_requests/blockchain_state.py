"""Block chain state related rpc requests."""

from app.utilities.rpc_request import send_rpc_request


def send_rpc_get_blockchain_state() -> dict | None:
    """Sends the `get_blockchain_state` rpc request to the full node.

    Returns None if the request fails.

    More can be read here:
    https://docs.chia.net/full-node-rpc#get_blockchain_state

    Returns:
        dict | None: the full node's rpc response
    """
    return send_rpc_request(
        service='full_node',
        endpoint='get_blockchain_state',
    )
