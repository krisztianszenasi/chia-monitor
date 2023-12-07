"""Block related views."""

from flask import Blueprint, abort, request

from app.entities.block import Block
from app.routes.utils import requires_api_key
from app.use_cases.block import get_block, get_blocks
from app.use_cases.blockchain_state import get_blockchain_state
from app.utilities.pagination import PaginationParams, get_pagination_params

block_bp = Blueprint('block_bp', __name__)


@block_bp.route('/blocks')
@requires_api_key
def get_blocks_view() -> dict:
    """Get list of a blocks in a paginated response.

    Possible query parameters:
        page_size(int): set the page size.
        page(int): set the page index.

    The default value for page_size is 10 and for page it is 1.
    The maximum allowed value for page_size is 200. If higher page size is
    requested it will be processed as 200.

    If blocks cannot be retrieved the response will be a message.

    Returns:
        dict: paginated response
    """
    params: PaginationParams = get_pagination_params(request.args)
    current_state = get_blockchain_state()

    if current_state is None:
        return {'message': 'Could not retrieve blocks\n'+'Are you sure that the full node is running?'}

    start = current_state.height - params.page_idx * params.page_size + 1
    end = current_state.height - ((params.page_idx - 1) * params.page_size) + 1

    return {
        'count': current_state.height,
        'prev_page': params.page_idx - 1 or None,
        'next_page': params.page_idx + 1 if start >= 0 else None,
        'results': [
            block.as_dict()
            for block in get_blocks(start=start, end=end)
        ],
    }


@block_bp.route('/blocks/<height_or_header>')
@requires_api_key
def get_block_detail(height_or_header: str) -> dict:
    """Get data for a single block.

    Returns 404 not found if the block does not exist in the blockchain.

    Args:
        height_or_header (str): header hash or height

    Returns:
        dict: data for the given block
    """
    block: Block
    if block := get_block(height_or_header):
        return block.as_dict()
    abort(404)
