# -*- coding: utf-8 -*-
import broker_mqtt
from paho.mqtt import client as mqtt_client
import time


def create_client(client_id: str = broker_mqtt.MqttSettings.ClientIdBroker.value) -> mqtt_client.Client:
	# client.username_pw_set(username, password)
	client = mqtt_client.Client(client_id)
	client.connect(
		broker_mqtt.MqttSettings.TestBrokerURL.value,
		broker_mqtt.MqttSettings.Port.value)
	return client


def publish(client,messages: list,*topics):
	topics = topics[0]
	if len(messages) != len(topics):
		print(f"Number of messages and topics must be equal.")
		return
	for topic,message in zip(topics,messages):
		get_status(client,topic,message)


def get_status(client,topic,msg):
	status = client.publish(topic,msg)
	if status[0] == 0:
		print(f"Send {msg} to topic `{topic}`")
	else:
		print(f"Failed to send message to topic {topic}")
