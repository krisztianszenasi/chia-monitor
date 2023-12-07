"""SSL certificate related utilities."""

import os

DEFAULT_CHIA_ROOT = '$HOME/.chia/mainnet'
CHIA_ROOT = os.path.expandvars(
    os.environ.get('CHIA_ROOT') or DEFAULT_CHIA_ROOT,
)
CONFIG_ROOT = CHIA_ROOT + '/config'
SSL_ROOT = CONFIG_ROOT + '/ssl'


def get_certificates_for(service: str) -> tuple:
    """Get the certificate files in a tuple for the given service.

    Args:
        service (str): name of the give chia service

    Returns:
        tuple: tuple of certificates
    """
    return (
        f'{SSL_ROOT}/{service}/private_{service}.crt',
        f'{SSL_ROOT}/{service}/private_{service}.key',
    )
