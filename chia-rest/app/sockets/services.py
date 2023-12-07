"""Services related sockets."""

from flask import request

from app import app, socketio
from app.sockets.utils import handle_api_key
from app.use_cases.daemon import start_service, stop_service


@socketio.on('connect', namespace='/services')
def handle_services_connect():
    """Handles connections to the services namespace."""
    if handle_api_key(namespace='/services'):
        app.logger.info(f'Client has connected to /services with sid {request.sid}.')


@socketio.on('disconnect', namespace='/services')
def handle_services_disconnect():
    """Handles disconnections from the services namespace."""
    app.logger.info(f'Client has disconnected from /services with sid {request.sid}.')


def background_task_start_service(service: str):
    if result := start_service(service):
        socketio.emit('started', result.as_dict(), namespace='/services')
    else:
        socketio.emit('start_failed', service, namespace='/services')


@socketio.on('start', namespace='/services')
def handle_start_service(service_name: str):
    """Handles the start service."""
    socketio.start_background_task(target=background_task_start_service, service=service_name)


def background_task_stop_service(service: str):
    if result := stop_service(service):
        socketio.emit('stopped', result.as_dict(), namespace='/services')
    else:
        socketio.emit('stop_failed', service, namespace='/services')


@socketio.on('stop', namespace='/services')
def handle_stop_service(service_name: str):
    """Handles the stop service."""
    socketio.start_background_task(target=background_task_stop_service, service=service_name)
