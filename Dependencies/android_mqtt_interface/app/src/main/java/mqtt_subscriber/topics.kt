package mqtt_subscriber

enum class MqttTopics(val topicName: String) {
    BmsSoc("bms/soc"),
    BmsTemp("bms/temp"),
    TestCounter("test_acc/counter"),
    ClimateTemp("climate/temp"),
    ClimateCO2("climate/co2"),
    ClimateHumidity("climate/temp"),
    BatteryVoltages("battery/cell_voltages")
}

enum class MqttSettings(val settings: String) {
    TestBroker("tcp://broker.emqx.io:1883"),
    Broker("tcp://mqttpi.local:1883"),
    ClientId("clientIdTestApp"),
    UserName("pi"),
    Password("pi")
}