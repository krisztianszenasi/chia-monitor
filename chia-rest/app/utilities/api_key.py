"""Utilities related to api keys."""

import os
import secrets


def api_key_missing() -> bool:
    """Check if api key present."""
    return os.environ.get('API_KEY') is None


def handle_missing_api_key():
    """Handle missing api key."""
    answer = input('No api key found.\nWould you like to generate one? (Y/N)\n')

    if answer.lower() in ['y', 'yes']:
        print(f'\n\nAPI_KEY={generate_api_key()}')
        print('\n\nPut the above line into your .env file.')
    else:
        print('\n\nIn order to use the service you need to set the API_KEY variable in your .env file.')


def generate_api_key() -> str:
    """Generates an api key."""
    return f'chiarest_{secrets.token_urlsafe(32)}'
