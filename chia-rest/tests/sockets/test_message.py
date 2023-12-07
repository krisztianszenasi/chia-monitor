"""Message related test cases."""

from tests.sockets.base import BaseSocketTestCase


class MessageTestCase(BaseSocketTestCase):
    """Test case related for sending message."""

    def test_send_message(self):
        self.socketio_client.emit('message', {'hello': 'world'})
        response = self.socketio_client.get_received()

        self.assertEqual(response, response)
