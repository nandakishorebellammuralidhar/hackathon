import logging
from pathlib import Path

from ....mqtt_component.mqtt_publisher import MqttPublisher
from ....utils.activity_state import ActivityState
from ....utils.helper_functions import read_json_from_file, correct_identifier, int_to_hex_string


class CANMqttController:
    publisher: MqttPublisher = None
    state_topic: str = None
    topic: str = None
    topic_dict: dict = {}
    frame_ids_and_topics_json_path: Path = None

    def __init__(self, topic: str, client_id: str, logger: logging.Logger, topics_json_path: Path,
                 mqtt_broker_ip: str = "192.168.0.4",
                 mqtt_broker_port: int = 1883, username=None, password=None):

        self.frame_ids_and_topics_json_path = topics_json_path.resolve()
        self.logger = logger
        self.state_topic = f"{topic}/state"
        self.topic = topic
        self.__update_topics_to_can_frame_id()
        self.publisher = MqttPublisher(mqtt_broker_ip, mqtt_broker_port, client_id, username, password)
        self.__activate_publisher()

    def __update_topics_to_can_frame_id(self):
        temp_dict: dict = read_json_from_file(self.frame_ids_and_topics_json_path)
        print(f"Path to file: {self.frame_ids_and_topics_json_path}")
        for key in temp_dict.keys():
            new_key: str = str(correct_identifier(hex(int(key))))
            self.topic_dict[new_key] = temp_dict[key]

    def __activate_publisher(self):
        if self.publisher is not None and self.publisher.connect():
            self.publisher.loop_start()
            self.publish(self.state_topic, f"{ActivityState.ACTIVE}")
        else:
            self.publisher = None

    def publish(self, topic: str, message) -> None:
        if self.publisher is not None:
            self.publisher.publish(topic, message)
            self.logger.info("Message published successfully.")
        else:
            self.logger.error("Message publish failed.")

    def publish_by_can_id(self, can_id: str, message):
        hex_can_id = int_to_hex_string(can_id)
        if not self.topic_dict.__contains__(hex_can_id):
            return
        self.publish(f"{self.topic}{self.topic_dict[hex_can_id]['topic']}", str(message))

    def error_topic(self, topic: str) -> None:
        self.publish(topic, "ERROR Detected")

    def stop(self) -> None:
        if self.publisher is None:
            (self.logger.info(f"{self.__class__.__name__} is not connected to mqtt-broker."))
            return
        self.publish(self.state_topic, f"{ActivityState.INACTIVE}")
        self.publisher.loop_stop()  # Stop loop
        self.publisher.disconnect()
        self.publisher = None
        self.logger.info(f"Disconnected {self.__class__.__name__} from mqtt-broker.")
