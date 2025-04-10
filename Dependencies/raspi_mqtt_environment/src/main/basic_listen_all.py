import logging
import time

import can

from .can.can_component import BatteryMqttController
from .can.can_component.base_controller import CANMqttController
from .can.can_dbc_management import CANDbReader
from .sensors.distance_sensor.distance_sensor import DistanceSensor
from .utils.helper_functions import get_all_can_frame_ids, int_to_hex_string
from .utils.vehicle_position import VehiclePosition

logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
LOGGER = logging.getLogger(__name__)

CAN_INTERFACE = "can0"
RETRY_DELAY = 1.0  # Time to wait before retrying CAN bus connection
MAX_RETRIES = 3  # Maximum number of retries for CAN bus connection

distance_sensors_list = [DistanceSensor(vehicle_position=VehiclePosition.FRONT_CENTER, trigger_pin=21, echo_pin=20),
                         DistanceSensor(vehicle_position=VehiclePosition.REAR_CENTER, trigger_pin=26, echo_pin=19)]


def start():
    for distance_sensor in distance_sensors_list:
        distance_sensor.start_measurement()

    retries = 0

    while retries < MAX_RETRIES:
        try:
            bus = can.Bus(channel=CAN_INTERFACE, interface="socketcan")
            LOGGER.info(f"Successfully connected to CAN bus {CAN_INTERFACE}")
            break
        except OSError as e:
            LOGGER.error(f"Error creating CAN bus: {e}. Retrying in {RETRY_DELAY} seconds...")
            retries += 1
            time.sleep(RETRY_DELAY)

    if retries == MAX_RETRIES:
        LOGGER.error(f"Failed to connect to CAN bus {CAN_INTERFACE} after {MAX_RETRIES} retries. Exiting.")
        exit(1)

    can_mqtt_instance_controller = {}
    can_mqtt_instances: list[CANMqttController] = [BatteryMqttController()]
    can_db = CANDbReader()

    for instance in can_mqtt_instances:
        for key in get_all_can_frame_ids(instance.frame_ids_and_topics_json_path):
            can_mqtt_instance_controller[key] = instance

    try:
        while True:
            try:
                message = bus.recv(timeout=5.0)
                if message is not None:
                    LOGGER.debug(message)
                    translated_message = can_db.interpret_can_message(message)
                    can_mqtt_instance_controller[int_to_hex_string(str(message.arbitration_id))].publish_by_can_id(
                        str(message.arbitration_id), translated_message)
                    LOGGER.info(
                        f"ID: 0x{message.arbitration_id:x}, Data: {message.data.hex()}, DLC: {message.dlc}, Timestamp: {message.timestamp}")
                    LOGGER.info(translated_message)
                else:
                    LOGGER.debug("No CAN message received within timeout.")

            except can.exceptions.CanError as e:
                LOGGER.error(f"CAN read error: {e}")
                try:
                    bus.shutdown()
                    bus = can.Bus(channel=CAN_INTERFACE, interface="socketcan")
                    LOGGER.info("CAN bus re-established.")
                except Exception as e:
                    LOGGER.error(f"Failed to re-establish CAN bus: {e}")

            except Exception as e:
                LOGGER.exception(f"An unexpected error occurred: {e}")

    except KeyboardInterrupt:
        print("Stopping sensors...")
        for distance_sensor in distance_sensors_list:
            distance_sensor.stop()
        print("Sensors stopped.")

        LOGGER.info("Exiting...")
    finally:
        if bus:
            try:
                bus.shutdown()
                LOGGER.info(f"CAN bus {CAN_INTERFACE} shut down.")
            except Exception as e:
                LOGGER.error(f"Error shutting down CAN bus: {e}")
