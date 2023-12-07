"""Daemon related use cases."""

from enum import Enum

from app.entities.mappers.services import (
    parse_rpc_response_to_start_service_result,
    parse_rpc_response_to_stop_service_result)
from app.entities.services import StartServiceResult, StopServiceResult
from app.rpc_requests.daemon import send_rpc_running_services, send_rpc_start_service, send_rpc_stop_service


class Service(Enum):
    """Service enum options."""

    FULL_NODE = 'chia_full_node'
    FARMER = 'chia_farmer'
    HARVESTER = 'chia_harvester'
    WALLET = 'chia_wallet'


def get_service_report() -> dict | None:
    """Get a list of running and not running services."""
    response = {'services': []}
    if rpc_response := send_rpc_running_services():
        for service in Service:
            response['services'].append({
                'name': service.value,
                'running': service.value in rpc_response.get('running_services', []),
            })
    return response


def start_service(service: str) -> StartServiceResult | None:
    """Start the given chia service."""
    if rpc_response := send_rpc_start_service(service):
        return parse_rpc_response_to_start_service_result(rpc_response)
    return None


def stop_service(service: str) -> StopServiceResult | None:
    """Start the given chia service."""
    if rpc_response := send_rpc_stop_service(service):
        return parse_rpc_response_to_stop_service_result(rpc_response)
    return None
