"""Base entity definitions."""


from dataclasses import asdict, dataclass


@dataclass(kw_only=True)
class BaseEntity:
    """Base class for entities."""

    def as_dict(self) -> dict:
        """Represent the entity as a dictionary.

        Returns:
            dict: representation
        """
        return asdict(self)
