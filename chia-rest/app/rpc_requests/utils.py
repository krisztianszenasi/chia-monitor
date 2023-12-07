"""RPC request related utilities."""

import json
from typing import Callable, List


def send_rpc_through_script_handler(script_function: Callable, script_args: List[str] = None) -> dict | None:
    """Sends the given script function and transforms the response into dict.

    Args:
        scrip_function (Callable): Function that calls a script.
        script_args (List[str], optional): . Defaults to None.

    Returns:
        dict: RPC response.
    """
    args = script_args or []
    process = script_function(*args)
    std_out, std_err = process.communicate()
    if std_out:
        try:
            return json.loads(std_out)
        except json.JSONDecodeError:
            print("Could not decode RPC response for CLI command.")
    return None
