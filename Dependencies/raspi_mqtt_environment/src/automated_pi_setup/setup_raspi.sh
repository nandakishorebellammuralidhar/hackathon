#!/bin/bash
echo "Python Venv"
./setup_python_venv.sh
echo "Mqtt Broker Install"
./setup_mqtt_broker.sh
echo "Start CAN Communication"
./start_can_communication.sh

wait

echo "Automated setup done!"

