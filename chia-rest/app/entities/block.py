"""Block entity declaration."""

from dataclasses import dataclass
from typing import List, Optional

from app.entities.base import BaseEntity


@dataclass(kw_only=True)
class Block(BaseEntity):
    """Data class representation of a chia block."""

    height: int
    header_hash: str
    farmer_address: str
    pool_address: str
    timestamp: str
    block_reward: float
    total_iters: int
    weight: int
    overflow: bool
    finished_challenge_slot_hashes: Optional[List[str]]
    finished_infused_challenge_slot_hashes: Optional[List[str]]
    finished_reward_slot_hashes: Optional[List[str]]
    signage_point_index: int
