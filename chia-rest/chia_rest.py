from dotenv import load_dotenv

from app import app, socketio
from app.utilities.api_key import api_key_missing, handle_missing_api_key
from app.utilities.welcome_screen import display_welcome_screen

if __name__ == '__main__':
    display_welcome_screen()

    load_dotenv()

    if api_key_missing():
        handle_missing_api_key()
    else:
        socketio.init_app(app)
        socketio.run(app, port=5001)
