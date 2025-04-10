package mqtt_subscriber


const val RASPI_2_IP: String = "192.168.18.70"

enum class MqttTopics(val topicName: String) {
    ANY("#"),
    TEMPERATURE_SENSOR_COCKPIT_CENTER("${SensorType.TEMPERATURE.value}/${VehiclePosition.COCKPIT_CENTER.value}"),
    TEMPERATURE_SENSOR_COCKPIT_LEFT("${SensorType.TEMPERATURE.value}/${VehiclePosition.COCKPIT_LEFT.value}"),
    TEMPERATURE_SENSOR_COCKPIT_RIGHT("${SensorType.TEMPERATURE.value}/${VehiclePosition.COCKPIT_RIGHT.value}"),
    TEMPERATURE_SENSOR_REAR_RIGHT("${SensorType.TEMPERATURE.value}/${VehiclePosition.REAR_RIGHT.value}"),
    TEMPERATURE_SENSOR_REAR_LEFT("${SensorType.TEMPERATURE.value}/${VehiclePosition.REAR_LEFT.value}"),
    TEMPERATURE_SENSOR_REAR_CENTER("${SensorType.TEMPERATURE.value}/${VehiclePosition.REAR_CENTER.value}"),
    DISTANCE_SENSOR_FRONT_CENTER("${SensorType.DISTANCE.value}/${VehiclePosition.FRONT_LEFT.value}"),
    DISTANCE_SENSOR_FRONT_LEFT("${SensorType.DISTANCE.value}/${VehiclePosition.FRONT_LEFT.value}"),
    DISTANCE_SENSOR_REAR_LEFT("${SensorType.DISTANCE.value}/${VehiclePosition.REAR_LEFT.value}"),
    DISTANCE_SENSOR_FRONT_RIGHT("${SensorType.DISTANCE.value}/${VehiclePosition.FRONT_RIGHT.value}"),
    DISTANCE_SENSOR_REAR_RIGHT("${SensorType.DISTANCE.value}/${VehiclePosition.REAR_RIGHT.value}"),
    DISTANCE_SENSOR_REAR_CENTER("${SensorType.DISTANCE.value}/${VehiclePosition.REAR_CENTER.value}"),
    GYROSCOPE_SENSOR_REAR_LEFT("${SensorType.GYROSCOPE.value}/${VehiclePosition.REAR_LEFT.value}"),
    GYROSCOPE_SENSOR_REAR_CENTER("${SensorType.GYROSCOPE.value}/${VehiclePosition.REAR_CENTER.value}"),
    GYROSCOPE_SENSOR_REAR_RIGHT("${SensorType.GYROSCOPE.value}/${VehiclePosition.REAR_RIGHT.value}"),
    TestCounter("test_acc/counter"),

}

enum class MqttSettings(val settings: String) {
    Broker("tcp://$RASPI_2_IP:1883"),
    ClientId("clientIdTestApp"),
}

enum class VehiclePosition(val value: String){
    FRONT_CENTER("front_center"),
    FRONT_LEFT ("front_left"),
    FRONT_RIGHT ("front_right"),
    REAR_LEFT("back_left"),
    REAR_RIGHT("back_right"),
    REAR_CENTER("back_center"),
    COCKPIT_CENTER("cockpit_center"),
    COCKPIT_LEFT("cockpit_left"),
    COCKPIT_RIGHT("cockpit_right"),
}

enum class SensorType(val value: String){
    TEMPERATURE("temperature_sensor"),
    DISTANCE("distance_sensor"),
    GYROSCOPE("gyroscope_sensor"),
}

enum class CanEndpoints(val value: String){
    BATTERY("battery"),
}