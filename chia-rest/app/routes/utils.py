"""Utilities for routes."""

import os
from functools import wraps

from flask import abort, request


def requires_api_key(func):
    """Decorator for requiring api keys on flask routes."""
    @wraps(func)
    def wrapper(*args, **kwargs):
        # Get the authorization header
        authorization_header = request.headers.get('Authorization')

        # Check if the Authorization header is present
        if not authorization_header:
            abort(401, "Missing API key")

        # Extract the API key from the header
        api_key = authorization_header.split(' ')[-1]

        valid_api_key = os.environ.get('API_KEY')

        # Validate the API key
        if api_key != valid_api_key:
            abort(403, "Invalid API key")

        # Call the original route function if the API key is valid
        return func(*args, **kwargs)

    return wrapper
