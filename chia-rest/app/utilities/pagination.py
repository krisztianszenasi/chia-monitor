"""Utilities related to pagination."""

from dataclasses import dataclass

DEFAULT_PAGE_IDX = 1
DEFAULT_PAGE_SIZE = 10
ALLOWED_MAX_PAGE_SIZE = 200


@dataclass(kw_only=True)
class PaginationParams:
    """Data class storing pagination parameters."""

    page_idx: int
    page_size: int


def get_pagination_params(query_params: dict) -> PaginationParams:
    """Build a `PaginationParams` dataclass from the given query params.

    Args:
        query_params (dict): flask request arguments

    Returns:
        PaginationParams: pagination params built
    """
    page_size = int(query_params.get('page_size', DEFAULT_PAGE_SIZE))
    return PaginationParams(
        page_idx=int(query_params.get('page', DEFAULT_PAGE_IDX)),
        page_size=min(page_size, ALLOWED_MAX_PAGE_SIZE),
    )
