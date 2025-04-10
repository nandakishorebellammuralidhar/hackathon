# -*- coding: utf-8 -*-

import random
import time

import broker_mqtt
import broker_mqtt.helper

broker = broker_mqtt.MqttSettings.TestBrokerURL.value
port = broker_mqtt.MqttSettings.Port.value
client_id = broker_mqtt.MqttSettings.ClientIdBroker.value

test_soc = broker_mqtt.Topics.TestSoc.value
test_temp = broker_mqtt.Topics.TestTemp.value
test_counter = broker_mqtt.Topics.TestCounter.value


def publish(client,*topics):
	msg_count = 1
	msg_soc = 100
	msg_temp = 25.3
	while True:
		time.sleep(1.5)
		messages = [msg_count,msg_soc,msg_temp]
		for topic,message in zip(topics,messages):
			broker_mqtt.helper.get_status(client,topic,message)

		msg_count += 1
		msg_soc -= 0.1
		msg_temp = random_float_with_step(25.0,28.0,0.2)

		if msg_count > 20000:
			client.disconnect()
			break


def run():
	client = broker_mqtt.helper.create_client()
	client.loop_start()
	publish(client,test_counter,test_soc,test_temp)
	client.loop_stop()


def random_float_with_step(start,stop,step):
	return random.uniform(start,stop) // step * step


if __name__ == '__main__':
	run()
