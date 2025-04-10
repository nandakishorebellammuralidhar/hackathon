import logging

import paho.mqtt.client as mqtt

LOGGER = logging.getLogger(__name__)
LOGGER.setLevel(logging.INFO)
HANDLER = logging.StreamHandler()
FORMATTER = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
HANDLER.setFormatter(FORMATTER)
LOGGER.addHandler(HANDLER)


class MqttPublisher:
    def __init__(self, broker_address, port, client_id="", username=None, password=None, protocol=mqtt.MQTTv5):
        self.broker_address = broker_address
        self.port = port
        self.client_id = client_id
        self.username = username
        self.password = password
        self.protocol = protocol
        self.client = None

    def connect(self):
        self.client = mqtt.Client()
        if self.username and self.password:
            self.client.username_pw_set(self.username, self.password)
        try:
            self.client.connect(self.broker_address, self.port, 60)
            LOGGER.info(f"Connected to {self.broker_address}:{self.port}")
            return True
        except Exception as e:
            LOGGER.exception(f"Connection failed: {e}")
            return False

    def publish(self, topic, message, qos=0, retain=False):
        if self.client is None:
            LOGGER.warning("Client not connected. Call connect() first.")
            return False

        try:
            result = self.client.publish(topic, message, qos, retain)
            if result[0] == mqtt.MQTT_ERR_SUCCESS:
                LOGGER.info(f"Published to {topic}: {message}")
                return True
            else:
                LOGGER.error(f"Publish failed: {result}")
                return False
        except Exception as e:
            LOGGER.exception(f"Publish error: {e}")
            return False

    def disconnect(self):
        if self.client:
            self.client.disconnect()
            LOGGER.info("Disconnected.")

    def loop_start(self):
        if self.client:
            self.client.loop_start()

    def loop_stop(self):
        if self.client:
            self.client.loop_stop()
