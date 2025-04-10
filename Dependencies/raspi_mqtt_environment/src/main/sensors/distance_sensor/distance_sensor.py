import logging
import threading
import time

import pigpio

from ...sensors.base_controller.sensor_mqtt_controller import SensorMqttController
from ...sensors.distance_sensor.distance_sensor_mqtt_controller import DistanceSensorMqttController
from ...utils.vehicle_position import VehiclePosition

LOGGER = logging.getLogger(__name__)
LOGGER.setLevel(logging.INFO)
HANDLER = logging.StreamHandler()
FORMATTER = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
HANDLER.setFormatter(FORMATTER)
LOGGER.addHandler(HANDLER)


class DistanceSensor:
    mqttPublisher: SensorMqttController
    _running = False

    def __init__(self, trigger_pin: int, echo_pin: int, vehicle_position: VehiclePosition, min_range: int = 0,
                 max_range: int = 800):
        self.__setup_pi(trigger_pin, echo_pin)
        self.trigger_pin = trigger_pin
        self.echo_pin = echo_pin
        self.vehicle_position = vehicle_position
        self._min_range = min_range
        self._max_range = max_range
        self.mqttPublisher = DistanceSensorMqttController(vehicle_position=vehicle_position)
        self.thread = threading.Thread(target=self.__check_distance_from_sensor)
        self.thread.daemon = True

    def __setup_pi(self, trigger_pin: int, echo_pin: int):
        self.pi = pigpio.pi()
        self.pi.set_mode(trigger_pin, pigpio.OUTPUT)
        self.pi.set_mode(echo_pin, pigpio.INPUT)
        pass

    def __check_distance_from_sensor(self):
        pulse_end = 0
        pulse_start = 0
        is_out_of_range = False
        out_of_range_counter = 3

        try:
            while self._running:

                self.pi.write(self.trigger_pin, 0)
                LOGGER.info("Waiting For Sensor To Settle")
                time.sleep(2)

                self.pi.write(self.trigger_pin, 1)
                time.sleep(0.00001)
                self.pi.write(self.trigger_pin, 0)

                while self.pi.read(self.echo_pin) == 0:
                    pulse_start = time.time()  # Time of the last LOW pulse

                while self.pi.read(self.echo_pin) == 1:
                    pulse_end = time.time()  # Time of the last HIGH pulse

                pulse_duration = pulse_end - pulse_start

                distance = pulse_duration * 17150
                distance = round(distance, 2)

                if self._min_range < distance < self._max_range:
                    self.send_data(f"Distance: {distance - 0.5}cm")
                    is_out_of_range = False
                else:
                    if not is_out_of_range or out_of_range_counter <= 0:
                        self.send_data("Out Of Range")
                        out_of_range_counter = 3
                        is_out_of_range = True
                    else:
                        out_of_range_counter -= 1

        except KeyboardInterrupt:
            self.send_data("Interrupted Connection to Distance Sensor.")
            self.pi.stop()

    def start_measurement(self):
        self._running = True
        LOGGER.info(
            f"Start measurement for {self.vehicle_position}-Distance-Sensor, on GPIO Pins:\nTrigger: '{self.trigger_pin}'\nEcho: '{self.echo_pin}' ")
        self.thread.start()

    def stop(self):
        LOGGER.info(
            f"Stopped measurement for {self.vehicle_position}-Distance-Sensor, on GPIO Pins:\nTrigger: '{self.trigger_pin}'\nEcho: '{self.echo_pin}' ")
        self.send_data("Interrupted Connection to Distance Sensor.")
        self._running = False

    def send_data(self, message):
        self.mqttPublisher.publish(message)


if __name__ == "__main__":
    test_distance_sensor = DistanceSensor(trigger_pin=21, echo_pin=20, vehicle_position=VehiclePosition.FRONT_CENTER)
    test_distance_sensor.start_measurement()
