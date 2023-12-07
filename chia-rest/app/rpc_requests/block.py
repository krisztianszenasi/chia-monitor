"""Block related rpc request definitions."""

from app.utilities.rpc_request import ResultKeyOption, send_rpc_request


def send_rpc_get_block_record_by_header_hash(header_hash: str) -> dict | None:
    """Send the `get_block_record` rpc request to the full node.

    Returns None if the request fails.

    More can be read here:
    https://docs.chia.net/full-node-rpc#get_block_record

    Args:
        header_hash (str): header hash of the block

    Returns:
        dict | None: the full node's rpc response
    """
    return send_rpc_request(
        service='full_node',
        endpoint='get_block_record',
        data={'header_hash': header_hash}
    )


def send_rpc_get_block_record_by_height(height: int) -> dict | None:
    """Sends the `get_block_record_by_height` rpc request to the full node.

    Returns None if the request fails.

    More can be read here:
    https://docs.chia.net/full-node-rpc#get_block_record_by_height

    Args:
        height (int): height of the desired block

    Returns:
        dict | None: the full node's rpc response
    """
    return send_rpc_request(
        service='full_node',
        endpoint='get_block_record_by_height',
        data={'height': height},
        result_key_option=ResultKeyOption(
            strategy=ResultKeyOption.Strategy.USE_CUSTOM,
            key='block_record',
        ),
    )


def send_rpc_get_block_records(start: int, end: int) -> dict | None:
    """Sends the `get_block_records` rpc request to the full node.

    Returns None if the request fails.

    More can be read here:
    https://docs.chia.net/full-node-rpc#get_block_records

    Args:
        start (int): first block
        end (int): last block (won't be included)

    Returns:
        dict | None: the full node's rpc response
    """
    return send_rpc_request(
        service='full_node',
        endpoint='get_block_records',
        data={'start': start, 'end': end},
    )


def send_rpc_get_additions_and_removals(header_hash: str) -> dict | None:
    """Sends the `get_additions_and_removals` rpc request to the full node.

    This particular request turned out to be very inconsistent. Some times it
    takes a lot of time for the full node to process. I'm guessing it might be
    because of the sqlite database which stores around 250 million coins.

    Returns None if the request fails.

    More can be read here:
    https://docs.chia.net/full-node-rpc#get_block_records

    Args:
        header_hash (str): header hash of the desired block

    Returns:
        dict | None: the full node's rpc response
    """
    return send_rpc_request(
        service='full_node',
        endpoint='get_additions_and_removals',
        data={'header_hash': header_hash},
        result_key_option=ResultKeyOption(
            strategy=ResultKeyOption.Strategy.USE_NOTHING,
        ),
    )
