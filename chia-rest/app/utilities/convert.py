"""Utility functions related to conversions."""


CHIA_TO_MOJO = 1_000_000_000_000


def convert_mojo_to_xch(mojo: float) -> float:
    return mojo / CHIA_TO_MOJO


def convert_xch_to_mojo(xch: float) -> float:
    return xch * CHIA_TO_MOJO
