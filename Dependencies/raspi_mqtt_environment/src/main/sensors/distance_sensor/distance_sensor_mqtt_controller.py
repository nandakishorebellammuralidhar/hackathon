import logging

from ...sensors.base_controller.sensor_mqtt_controller import SensorMqttController
from ...utils.vehicle_position import VehiclePosition

LOGGER = logging.getLogger(__name__)
LOGGER.setLevel(logging.INFO)
HANDLER = logging.StreamHandler()
FORMATTER = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
HANDLER.setFormatter(FORMATTER)
LOGGER.addHandler(HANDLER)

DISTANCE_SENSOR_TOPIC = "distance_sensor"


class DistanceSensorMqttController(SensorMqttController):
    client_id: str = "distance_sensor"

    def __init__(self, vehicle_position: VehiclePosition, mqtt_broker_ip: str = "192.168.0.4",
                 mqtt_broker_port: int = 1883, username=None, password=None):
        sensor_topic = f"{DISTANCE_SENSOR_TOPIC}/{vehicle_position.value}"
        super().__init__(sensor_topic, self.client_id, LOGGER, mqtt_broker_ip,
                         mqtt_broker_port, username, password)
