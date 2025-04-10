#!/bin/bash

echo "############# Update Raspi for Mqtt-Installation #######################"
apt-get update -y
apt-get upgrade -y
apt-get install mosquitto mosquitto-clients -y
echo "listener 1883" >> /etc/mosquitto/mosquitto.conf
echo "allow_anonymous true" >> /etc/mosquitto/mosquitto.conf
systemctl enable mosquitto.service
systemctl status mosquitto
echo "#########################################################################"