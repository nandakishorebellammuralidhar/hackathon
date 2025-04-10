import logging

from ...mqtt_component.mqtt_publisher import MqttPublisher
from ...utils.activity_state import ActivityState


class SensorMqttController:
    publisher: MqttPublisher = None
    state_topic: str = None
    topic: str = None

    def __init__(self, topic: str, client_id: str, logger: logging.Logger,
                 mqtt_broker_ip: str = "192.168.0.4",
                 mqtt_broker_port: int = 1883, username=None, password=None):

        self.logger = logger
        self.state_topic = f"{topic}/state"
        self.topic = topic
        self.publisher = MqttPublisher(mqtt_broker_ip, mqtt_broker_port, client_id, username, password)
        self.__activate_publisher()

    def __activate_publisher(self):
        if self.publisher is not None and self.publisher.connect():
            self.publisher.loop_start()
            self._publish(self.state_topic, f"{ActivityState.ACTIVE}")
        else:
            self.publisher = None

    def publish(self, message):
        self._publish(None, message)

    def _publish(self, topic: str | None, message) -> None:
        if self.publisher is not None:
            topic = self.topic if topic is None else topic
            self.publisher.publish(topic, message)
            self.logger.info("Message published successfully.")
        else:
            self.logger.error("Message publish failed.")

    def error_topic(self, topic: str) -> None:
        self.publish(topic, "ERROR Detected")

    def stop(self) -> None:
        if self.publisher is None:
            (self.logger.info(f"{self.__class__.__name__} is not connected to mqtt-broker."))
            return
        self._publish(self.state_topic, f"{ActivityState.INACTIVE}")
        self.publisher.loop_stop()  # Stop loop
        self.publisher.disconnect()
        self.publisher = None
        self.logger.info(f"Disconnected {self.__class__.__name__} from mqtt-broker.")
