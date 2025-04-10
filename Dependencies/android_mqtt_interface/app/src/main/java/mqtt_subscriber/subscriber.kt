package mqtt_subscriber

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

fun main() {
//    val broker = "tcp://broker.emqx.io:1883"
    val broker = MqttSettings.TestBroker.settings
    val clientId = "KotlinClient"
//    val topic = "battery/cell_voltages"
    val persistence = MemoryPersistence()
    var text = ""

    try {
        val client = MqttClient(broker, clientId, persistence)
        val connOpts = MqttConnectOptions()
        connOpts.isCleanSession = true
        println("Connecting to broker: $broker")
        client.connect(connOpts)
        println("Connected")

        client.subscribe("bms/soc") { topic, message ->
            println("Message received: ${message.payload.toString(Charsets.UTF_8)}")
            text = message.payload.toString(Charsets.UTF_8)
        }

        client.subscribe("bms/temp") { topic, message ->
            println("Message2 received: ${message.payload.toString(Charsets.UTF_8)}")
            text = message.payload.toString(Charsets.UTF_8)
        }

        // Keep the client running to listen for messages
        Thread.sleep(600000)
        client.disconnect()
        println("Disconnected")
    } catch (e: Exception) {
        e.printStackTrace()
    }
    println("hallo welt")
}