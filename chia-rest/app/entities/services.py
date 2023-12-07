"""Services related entities."""

from dataclasses import dataclass

from app.entities.base import BaseEntity


@dataclass(kw_only=True)
class StartServiceResult(BaseEntity):
    """Data class representation of the start service response."""

    error: str | None
    service: str
    success: bool

    @property
    def is_successful(self) -> bool:
        return self.error is None


@dataclass(kw_only=True)
class StopServiceResult(BaseEntity):
    """Data class representation of the stop service response."""

    service: str
    success: bool
