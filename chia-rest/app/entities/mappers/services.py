"""Service related mappers."""

from app.entities.services import StartServiceResult, StopServiceResult


def parse_rpc_response_to_start_service_result(rpc_response: dict) -> StartServiceResult | None:
    try:
        return StartServiceResult(
            error=rpc_response['error'],
            service=rpc_response['service'],
            success=rpc_response['success'],
        )
    except KeyError:
        return None


def parse_rpc_response_to_stop_service_result(rpc_response: dict) -> StopServiceResult | None:
    try:
        return StopServiceResult(
            service=rpc_response['service_name'],
            success=rpc_response['success'],
        )
    except KeyError:
        return None
