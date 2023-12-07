"""Blockchain state related sockets."""

from app import socketio
from app.sockets.utils import BackgroundEmitLooper, BackgroundLooperNameSpace
from app.use_cases.blockchain_state import get_blockchain_state


def send_blockchain_state():
    """Send current blockchain state if exists."""
    if state := get_blockchain_state():
        socketio.emit('current', state.as_dict(), namespace='/blockchain-state')


socketio.on_namespace(
    BackgroundLooperNameSpace(
        '/blockchain-state',
        looper=BackgroundEmitLooper(
            emit_function=send_blockchain_state,
            delay=1,
        ),
    ),
)
