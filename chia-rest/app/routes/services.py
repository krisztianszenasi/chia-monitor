"""Services related routes."""

from app.routes.utils import requires_api_key
from app.use_cases.daemon import get_service_report
from flask import Blueprint

services_bp = Blueprint('services_bp', __name__)


@services_bp.route('/service-report')
@requires_api_key
def route_service_report():
    """Retrieve the service report."""
    return get_service_report()
