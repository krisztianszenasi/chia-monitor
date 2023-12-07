"""Blockchain state related views."""

from flask import Blueprint

from app.routes.utils import requires_api_key
from app.use_cases.blockchain_state import get_blockchain_state

blockchain_state_bp = Blueprint('blockchain_state_bp', __name__)


@blockchain_state_bp.route('/blockchain-state')
@requires_api_key
def get_current_blockchain_state() -> dict:
    """Returns the current state of the blockchain.

    Returns:
        dict: 
    """
    if current_state := get_blockchain_state():
        return current_state.as_dict()
    return {'message': 'Could not retrieve blockchain state. Are your sure its running?'}
