"""Use cases for blocks."""

from app.entities.block import Block
from app.entities.mappers.block import parse_rpc_response_to_block
from app.rpc_requests.block import (send_rpc_get_block_record_by_header_hash,
                                    send_rpc_get_block_record_by_height,
                                    send_rpc_get_block_records)


def get_block(header_or_height: str) -> Block | None:
    """Get the given block as a `Block` entity.

    Returns None if the the block can not be found or
    the underlying rpc request fails.

    Args:
        header_or_height (str): header hash or height

    Returns:
        Block | None: Retrieved block
    """
    try:
        return get_block_by_height(int(header_or_height))
    except ValueError:
        return get_block_by_header_hash(header_or_height)


def get_block_by_header_hash(header_hash: str) -> Block | None:
    """Get the given block by it's header hash as a `Block` entity.

    Returns None if the the block can not be found or
    the underlying rpc request fails.

    Args:
        header_hash (str): header hash of block

    Returns:
        Block | None: Retrieved block.
    """
    if block_data := send_rpc_get_block_record_by_header_hash(header_hash):
        return parse_rpc_response_to_block(block_data)
    return None


def get_block_by_height(height: int) -> Block | None:
    """Get the given block by it's height as a `Block` entity.

    Returns None if the the block can not be found or
    the underlying rpc request fails.

    Args:
        height (int): height of the block

    Returns:
        Block | None: Retrieved block.
    """
    if block_data := send_rpc_get_block_record_by_height(height):
        return parse_rpc_response_to_block(block_data)
    return None


def get_blocks(start: int, end: int) -> list[Block]:
    """Retrieve block for the given range as a `Block` entity list.

    Returns an empty list if the the blocks can not be found or
    the underlying rpc request fails.

    Args:
        start (int): height of the first block
        end (int): height of the last block (won't be included)

    Returns:
        list[Block]: List of block retrieved
    """
    if block_data_list := send_rpc_get_block_records(start=start, end=end):
        return [
            parse_rpc_response_to_block(block_data)
            for block_data in block_data_list[::-1]
        ]
    return []
