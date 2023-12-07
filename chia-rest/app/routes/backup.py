"""Backup related views."""

from subprocess import SubprocessError

from flask import Blueprint

from app.managers.backup_manager import BackupManager
from app.routes.utils import requires_api_key

backup_bp = Blueprint('backup_bp', __name__)

backup_manager = BackupManager()


@backup_bp.route('/current')
@requires_api_key
def get_current_backup() -> dict:
    """Route for retrieving the current backup.

    Returns:
        dict: backup response
    """
    if backup_manager.in_progress:
        return {
            'backup': None,
            'in_progress': True
        }
    backup = backup_manager.current_backup_with_height
    return {
        'backup': backup.as_dict() if backup else None,
        'in_progress': False,
    }


@backup_bp.route('/current', methods=['DELETE'])
@requires_api_key
def delete_current_backup() -> dict:
    """Deletes the current backup file.

    It will return successfully deleted message no matter what.

    Returns:
        dict: message response
    """
    backup_manager.delete_current()
    return {'message': 'Backup successfully deleted.'}


@backup_bp.route('/', methods=['POST'])
@requires_api_key
def create_backup() -> dict:
    """Starts a backup creation subprocess.

    It will be run asynchronously via the BackupManager singleton.
    Progress of the backup process can be checked via sockets in the
    `backup` namespace listening for `progress` events.

    Returns:
        dict: message response
    """
    message = 'Backup process successfully started.'
    try:
        backup_manager.create_backup()
    except SubprocessError as su_exc:
        message = su_exc.args[0]
    except FileExistsError as fi_exc:
        message = fi_exc.args[0]
    return {'message': message}
