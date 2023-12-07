"""Daemon related rpc requests."""

from app.rpc_requests.utils import send_rpc_through_script_handler
from app.scripts.script_handler import (get_running_services, start_chia,
                                        stop_chia)
from app.utilities.rpc_request import send_rpc_request


def send_rpc_running_services() -> dict | None:
    """Sends the `running_services` rpc request to the daemon.

    Returns None if the request fails.

    More can be read here:
    https://docs.chia.net/daemon-rpc#running_services

    Returns:
        dict | None: the daemon's rpc response
    """
    return send_rpc_through_script_handler(get_running_services)


def send_rpc_start_service(service_name: str) -> dict | None:
    """Sends the `start_service` rpc request to the daemon.

    Returns None if the request fails.

    More can be read here:
    https://docs.chia.net/daemon-rpc#start_service

    Returns:
        dict | None: the daemon's rpc response
    """
    return send_rpc_through_script_handler(start_chia, script_args=[service_name])


def send_rpc_stop_service(service_name: str) -> dict | None:
    """Sends the `stop_service` rpc request to the daemon.

    Returns None if the request fails.

    More can be read here:
    https://docs.chia.net/daemon-rpc#stop_service

    Returns:
        dict | None: the daemon's rpc response
    """
    return send_rpc_through_script_handler(stop_chia, script_args=[service_name])
