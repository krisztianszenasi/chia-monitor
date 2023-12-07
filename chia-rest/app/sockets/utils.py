"""Socket related utilities."""

import os
from threading import Event, Lock
from typing import Callable

from flask import request
from flask_socketio import Namespace, disconnect

from app import app, socketio


def handle_api_key(namespace: str) -> bool:
    """Handle wether the API key is valid.

    Args:
        namespace (str): socket namespace

    Returns:
        bool: True if the client has the correct api key
    """
    api_key = request.args.get('api_key')
    if not api_key:
        disconnect(request.sid)
        app.logger.debug('Client disconnected because of missing API key.')
        return False

    valid_api_key = os.environ.get('API_KEY')
    if api_key != valid_api_key:
        disconnect(request.sid)
        app.logger.warning('Client disconnected because of wrong API key.')
        return False

    return True


class BackgroundEmitLooper:
    """Handles emit functions that should run in the background."""

    def __init__(self, emit_function: Callable, delay: int):
        self._emit_function = emit_function
        self._delay = delay
        self._event = Event()
        self._thread = None
        self._lock = Lock()

    def start_loop(self):
        with self._lock:
            if self._thread is None:
                self._event.set()
                self._thread = socketio.start_background_task(self._background_loop)
                app.logger.debug(
                    f'Background loop started for {self._emit_function.__name__} with {self._delay}s delays.'
                )

    def stop_loop(self):
        self._event.clear()
        with self._lock:
            if self._thread is not None:
                self._thread.join()
                self._thread = None
                app.logger.debug(f'Background loop stopped for {self._emit_function.__name__}.')

    def _background_loop(self):
        try:
            while self._event.is_set():
                self._emit_function()
                socketio.sleep(self._delay)
        finally:
            self._event.clear()
            self._thread = None


class BackgroundLooperNameSpace(Namespace):
    """Custom name space that uses background tasks."""

    def __init__(self, *args, looper: BackgroundEmitLooper, **kwargs):
        super().__init__(*args, **kwargs)
        self._looper = looper
        self._clients = 0

    def on_connect(self):
        if handle_api_key(self.namespace):
            app.logger.info(f'Client has connected to {self.namespace} with sid {request.sid}.')
            self._clients += 1
            self._looper.start_loop()

    def on_disconnect(self):
        app.logger.info(f'Client has disconnected from {self.namespace} with sid {request.sid}.')
        self._clients -= 1
        if self._clients == 0:
            self._looper.stop_loop()
