"""Block related mappers."""

from app.entities.block import Block
from app.utilities.convert import convert_mojo_to_xch


def parse_rpc_response_to_block(rpc_response: dict) -> Block | None:
    """Parse the rpc response to a `Block` entity.

    If anything goes bad return None

    Args:
        rpc_response (dict): rpc response with the relevant part only

    Returns:
        Block: parsed block
    """
    try:
        return Block(
            height=rpc_response['height'],
            header_hash=rpc_response['header_hash'],
            farmer_address=rpc_response['farmer_puzzle_hash'],
            pool_address=rpc_response['pool_puzzle_hash'],
            timestamp=rpc_response['timestamp'],
            block_reward=get_block_reward(rpc_response),
            total_iters=rpc_response['total_iters'],
            weight=rpc_response['weight'],
            overflow=rpc_response['overflow'],
            finished_challenge_slot_hashes=rpc_response['finished_challenge_slot_hashes'],
            finished_infused_challenge_slot_hashes=rpc_response['finished_infused_challenge_slot_hashes'],
            finished_reward_slot_hashes=rpc_response['finished_reward_slot_hashes'],
            signage_point_index=rpc_response['signage_point_index'],
        )
    except KeyError:
        return None


def get_block_reward(rpc_response: dict) -> float | None:
    """Retrieves the sum of block rewards."""
    if reward_claims := rpc_response.get('reward_claims_incorporated'):
        return sum([
            convert_mojo_to_xch(reward.get('amount', 0))
            for reward in reward_claims
        ])
    return None
