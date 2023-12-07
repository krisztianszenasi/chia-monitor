"""Module for handling chia node related shell scripts."""

import subprocess


def start_chia(service: str) -> subprocess.Popen:
    """Start the given chia services."""
    return run_script(name='start_chia.sh', args=[service])


def stop_chia(service: str) -> subprocess.Popen:
    """Stop the given chia services."""
    return run_script(name='stop_chia.sh', args=[service])

def get_running_services() -> subprocess.Popen:
    """Get the list of running services."""
    return run_script(name='running_services.sh')


def create_backup() -> subprocess.Popen:
    """Create backup from the blockchain database."""
    return run_script(name='backup_db.sh')


def run_script(*, name: str, args: list[str] = None) -> subprocess.Popen:
    """Runs the given shell script in a subprocess.

    Scripts can be selected from the `./sh_scripts` folder. They must be
    executable so check the permissions if you extend them.

    Scripts will be run in a subprocess asynchronously. They can be killed
    via the return `Popen` instance.

    Args:
        name (str): Name of the script.
        args (list[str], optional): Optional argument list.

    Returns:
        subprocess.Popen: reference to the running subprocess
    """
    args = args or []
    return subprocess.Popen(
        [f'./app/scripts/sh_scripts/{name}', *args],
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        stdin=subprocess.PIPE
    )
