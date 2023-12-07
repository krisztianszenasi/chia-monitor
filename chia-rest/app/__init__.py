"""Initialize socketio and flask app."""

from flask import Flask
from flask_socketio import SocketIO

from app.routes.backup import backup_bp
from app.routes.block import block_bp
from app.routes.blockchain_state import blockchain_state_bp
from app.routes.services import services_bp
from app.utilities.logging import configure_logging

socketio = SocketIO()
app = Flask(__name__)
app.debug = False

configure_logging()

app.register_blueprint(block_bp)
app.register_blueprint(blockchain_state_bp)
app.register_blueprint(services_bp)
app.register_blueprint(backup_bp, url_prefix='/backup')

# sockets needs to be imported otherwise
# the code in it wouldn't be executed
from app import sockets