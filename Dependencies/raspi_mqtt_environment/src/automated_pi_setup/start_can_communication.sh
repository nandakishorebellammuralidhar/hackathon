#!/bin/bash

echo "########## MCP Detected #######"
dmesg | grep mcp
echo "###############################"
echo "######## Installing CAN Utils ###########"
apt-get install can-utils -y
echo "################################"
echo "######### CAN Link set ##############"
ip link set can0 up type can bitrate 500000 restart-ms 100
ip addr | grep can
echo "\n####################################"
echo "#########################################"
echo "############# Start CAN listener ###################"
candump -tz can0 &