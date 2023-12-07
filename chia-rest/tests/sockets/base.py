"""Base definitions for socket related test cases."""

import unittest

from flask_socketio import SocketIOTestClient

from app import app, socketio


class BaseSocketTestCase(unittest.TestCase):
    """Base TestCase class for socket related tests.

    The `socketio_client` is an instance of `SocketIOTestClient` which can be
    used to test the given socket events. It is configured by default to
    connect to the main apps flask app and flask_socketio socketio variables.
    """

    def setUp(self):
        self.app = app.test_client()
        self.socketio_client: SocketIOTestClient = SocketIOTestClient(app, socketio)
        self.socketio_client.connect()

    def tearDown(self):
        self.socketio_client.disconnect()
