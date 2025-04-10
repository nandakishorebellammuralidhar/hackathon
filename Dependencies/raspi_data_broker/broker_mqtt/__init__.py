# -*- coding: utf-8 -*-

from enum import Enum


class MqttSettings(Enum):
	"""Settings for Mqtt-Broker"""
	Port: int = 1883
	TestBrokerURL: str = "broker.emqx.io"
	ClientIdBroker: str = "AccMqttBroker"
	SendIntervalInterval = 1


class Topics(Enum):
	"""Available topics for Mqtt-Broker."""
	BmsSoc = "bms/soc"
	ClimateTemp = "climate/temp"
	ClimateHumidity = "climate/humidity"
	ClimateCo2 = "climate/co2"
	TestTemp = "test/temp"
	TestCounter = "test/counter"
	TestSoc = "test/soc"


