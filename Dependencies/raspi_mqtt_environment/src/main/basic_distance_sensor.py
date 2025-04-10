import time

from .sensors.distance_sensor.distance_sensor import DistanceSensor
from .utils.vehicle_position import VehiclePosition


def start():
    distance_sensor = DistanceSensor(vehicle_position=VehiclePosition.FRONT_CENTER, trigger_pin=21, echo_pin=20)
    distance_sensor.start_measurement()

    try:
        while True:
            print("still here")
            time.sleep(5)

    except KeyboardInterrupt:
        print("Stopping sensors...")
        distance_sensor.stop()
        print("Sensors stopped.")
