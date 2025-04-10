import logging
from pathlib import Path

from ...can.can_component.base_controller.can_mqtt_controller import CANMqttController
from ...utils.helper_functions import get_project_path_for

LOGGER = logging.getLogger(__name__)
LOGGER.setLevel(logging.INFO)
HANDLER = logging.StreamHandler()
FORMATTER = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
HANDLER.setFormatter(FORMATTER)
LOGGER.addHandler(HANDLER)

BATTERY_TOPIC = "battery"
RELATIVE_PATH_TO_JSON = "can_dbc_management/topic/battery_topics.json"


def get_battery_topics_path() -> Path:
    return get_project_path_for(__file__, RELATIVE_PATH_TO_JSON, parent_level=1)


class BatteryMqttController(CANMqttController):
    frame_ids_and_topics_json = Path.resolve(get_battery_topics_path())
    client_id: str = "battery_publisher"

    def __init__(self, mqtt_broker_ip: str = "192.168.0.4", mqtt_broker_port: int = 1883, username=None, password=None):
        super().__init__(BATTERY_TOPIC, self.client_id, LOGGER, self.frame_ids_and_topics_json, mqtt_broker_ip,
                         mqtt_broker_port, username, password)
