package mqtt_subscriber


import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence


fun createMqttSession(): MqttClient? {
    val persistence = MemoryPersistence()
    try {
        val client = MqttClient(
            MqttSettings.Broker.settings,
            MqttSettings.ClientId.settings,
            persistence
        )
        val connOpts = MqttConnectOptions()
        connOpts.isCleanSession = true
        client.connect(connOpts)
        return client
    } catch (e: Exception) {
        println("Connection Error: $e")
        return null

    }
}
