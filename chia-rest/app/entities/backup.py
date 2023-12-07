"""Blockchain backup related entity."""

from dataclasses import dataclass
from datetime import datetime

from app.entities.base import BaseEntity


@dataclass(kw_only=True)
class Backup(BaseEntity):
    """Data class representation of chia database backup file."""

    name: str
    size: int
    height: int | None = None
    created: datetime

    def as_dict(self):
        result = super().as_dict()
        result['created'] = self.created.isoformat()
        return result


@dataclass(kw_only=True)
class BackupProgress(BaseEntity):
    """Data class representation of backup progress from a given timestamp."""

    progress: float | None = None
    current_size: int | None = None
    goal_size: int | None = None
