""""Block chain state definitions."""


from dataclasses import dataclass

from app.entities.base import BaseEntity


@dataclass(kw_only=True)
class BlockChainState(BaseEntity):
    """Data class representation of the blockchain state."""

    node_id: str
    height: int
    block_max_cost: int
    difficulty: int
    space: int
    syncing: bool
    sync_progress_height: int
    sync_tip_height: int
