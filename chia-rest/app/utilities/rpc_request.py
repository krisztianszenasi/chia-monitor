"""RPC request related utilities."""

import json
import os
from dataclasses import dataclass
from enum import Enum

import requests
import urllib3

from app.utilities.certificate import get_certificates_for

# warnings are disabled because these functions use
# self signed certificates by chia
urllib3.disable_warnings()


DEFAULT_RPC_ROOT = 'https://localhost'
RPC_ROOT = os.getenv('RPC_ROOT', DEFAULT_RPC_ROOT)


@dataclass(kw_only=True)
class ResultKeyOption:
    """Data class for handling rpc responses from various chia services.

    Chia services usually have responses like this:
    {'block': ..., 'success': True}

    With the help of this class the relevant part of the response can be
    retrieved.

    Three strategies are supported:

        USE_DEFAULT:
            Most of the time the key of the relevant part can be determined
            via the name of the rpc command. For example the `get_block`
            command will have a key `block`.

            The USE_DEFAULT strategy will remove the `get_` part of the command
            an use the remaining string as result key.

        USE_CUSTOM:
            Sometimes the default approach does not work. With the USE_CUSTOM
            strategy a custom key can be set with the `key` attribute.

        USE_NOTHING:
            This strategy will return the response without any kind of
            modifications.

    The actual logic determined by the selected strategy will be executed in
    the `retrieve_result` function.
    """

    class Strategy(Enum):
        USE_DEFAULT = 'use_default'
        USE_CUSTOM = 'use_custom'
        USE_NOTHING = 'use_nothing'

    strategy: Strategy = Strategy.USE_DEFAULT
    key: str | None = None

    def retrieve_result(self, rpc_response: dict, end_point: str) -> dict:
        """Retrieves the relevant part of the rpc response.

        Args:
            rpc_response (dict): rpc response from a chia service
            end_point (str): end point the response coming from
                (same as the rpc command name)

        Returns:
            dict: relevant part of the rpc response
        """
        if self.strategy == self.Strategy.USE_NOTHING:
            return rpc_response
        return rpc_response[self._get_key_for(end_point)]

    def _get_key_for(self, end_point: str) -> str:
        if self.strategy == self.Strategy.USE_CUSTOM:
            return self.key
        return self._get_default_key_for(end_point)

    def _get_default_key_for(self, end_point: str) -> str:
        return end_point.replace('get_', '')


service_port_mapping = {
    'daemon': 55400,
    'full_node': 8555,
    'farmer': 8559,
    'harvester': 8560,
    'wallet': 9256,
    'data_layer': 8562,
}


def send_rpc_request(
    *,
    service: str,
    endpoint: str,
    data: dict = None,
    result_key_option: ResultKeyOption = None,
    raise_exception: bool = False,
) -> dict:
    """Sends the rpc request and retrieves the relevant part of it.

    The retrieve strategy by default is USE_DEFAULT    

    Args:
        service (str): service to send request to
        endpoint (str): name of the rpc command
        data (dict): optional data to send
        result_key_option (ResultKeyOption): result retrieve strategy
        raise_exception (bool): flag for turning exception raising on/off

    Raises:
        exc: Raised if something went wrong with the request.
            If exceptions are turned of the function returns simply None

    Returns:
        dict: relevant part of the rpc response
    """
    result_key_option = result_key_option or ResultKeyOption()
    try:
        return result_key_option.retrieve_result(
            rpc_response=perform_rpc_request(
                service=service,
                endpoint=endpoint,
                data=data,
            ),
            end_point=endpoint,
        )
    except Exception as exc:
        if raise_exception:
            raise exc
    return None


def perform_rpc_request(
    *, service: str, endpoint: str, data: dict = None
) -> dict:
    """Actual code that sends the rpc request.

    Dedicated certificates must be used for the given services.
    Verify is turned off since these are self signed certificates by chia.

    Args:
        service (str): chia service
        endpoint (str): rpc command name
        data (dict, optional): optional data to the request

    Returns:
        dict: rpc response from the given service
    """
    response = requests.post(
        f'{RPC_ROOT}:{service_port_mapping[service]}/{endpoint}',
        headers={'Content-Type': 'application/json'},
        data=json.dumps(data or {}),
        cert=get_certificates_for(service),
        verify=False,
    )
    return json.loads(response.text)
