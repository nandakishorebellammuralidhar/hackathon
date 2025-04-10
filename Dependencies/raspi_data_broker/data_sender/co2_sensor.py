# -*- coding: utf-8 -*-
"""Publishing and reading the data from CO2-Sensor.
"""
import os
import pathlib
import sys

sys.path.append(str(pathlib.Path(os.getcwd())))

# pylint=disable:import-order
import time
import board
import busio
import adafruit_scd30
import broker_mqtt.helper
import broker_mqtt

i2c = busio.I2C(board.SCL,board.SDA,frequency=50000)
scd = adafruit_scd30.SCD30(i2c)


def main():
	client = broker_mqtt.helper.create_client()
	topics = (
	broker_mqtt.Topics.ClimateCo2.value,broker_mqtt.Topics.ClimateTemp.value,broker_mqtt.Topics.ClimateHumidity.value)

	while True:
		if scd.data_available:
			print("Data Available!")
			print(f"CO2: %d PPM {scd.CO2}")
			print(f"Temperature: %0.2f degrees C {scd.temperature}")
			print(f"Humidity: %0.2f %% rH {scd.relative_humidity}\n")
			print("Waiting for new data...\n")
			messages = [round(scd.CO2,2),round(scd.temperature,2),round(scd.relative_humidity,2)]

			broker_mqtt.helper.publish(client,messages,topics)

		time.sleep(1)


if __name__ == "__main__":
	print("Start Measurement")
	main()
