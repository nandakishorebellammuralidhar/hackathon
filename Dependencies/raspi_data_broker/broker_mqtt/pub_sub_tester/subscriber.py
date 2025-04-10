# -*- coding: utf-8 -*-

from paho.mqtt import client as mqtt_client

import broker_mqtt.helper

test_soc = broker_mqtt.Topics.ClimateCo2.value
test_temp = broker_mqtt.Topics.ClimateTemp.value
test_counter = broker_mqtt.Topics.ClimateHumidity.value


def subscribe(client: mqtt_client,*topics):
	def on_message(a,b, msg):
		print(f"Received `{msg.payload.decode()}` from topic `{msg.topic}`")

	for topic in topics:
		print(f"Subscribed to topic {topic}")
		client.subscribe(topic)
	client.on_message = on_message


def run():
	client = broker_mqtt.helper.create_client("sadkkasdklda")
	subscribe(client,test_counter,test_temp,test_soc)
	client.loop_forever()


if __name__ == '__main__':
	run()
