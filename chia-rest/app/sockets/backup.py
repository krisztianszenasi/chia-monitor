"""Blockchain state related sockets."""

from app import socketio
from app.managers.backup_manager import BackupManager
from app.sockets.utils import BackgroundEmitLooper, BackgroundLooperNameSpace

backup_manager = BackupManager()

def send_backup_progress():
    """Emit current backup progress to clients."""
    if backup_manager.in_progress and backup_manager.current_progress is not None:
        socketio.emit(
            'progress',
            backup_manager.current_progress.as_dict(),
            namespace='/backup'
        )

    if not backup_manager.in_progress and backup_manager.current_backup is not None:
        socketio.emit(
            'finished',
            namespace='/backup',
        )


socketio.on_namespace(
    BackgroundLooperNameSpace(
        '/backup',
        looper=BackgroundEmitLooper(
            emit_function=send_backup_progress,
            delay=1,
        ),
    ),
)
