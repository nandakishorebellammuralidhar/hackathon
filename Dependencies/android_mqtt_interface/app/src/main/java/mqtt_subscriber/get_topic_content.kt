package mqtt_subscriber


import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence


fun createMqttSession(): MqttClient {
    val persistence = MemoryPersistence()
    try {
        val client = MqttClient(
            MqttSettings.TestBroker.settings,
            MqttSettings.ClientId.settings,
            persistence
        )
        val connOpts = MqttConnectOptions()
        connOpts.isCleanSession = true
//        connOpts.userName = MqttSettings.UserName.toString()
//        connOpts.password = MqttSettings.Password.toString().toCharArray()
        client.connect(connOpts)
        return client
    } catch (e: Exception) {
        throw e

    }
}

//fun sendMyVerdammtenMqttMessage(message: String): String {
////    println(message)
//    return message
//}

//fun getTopicContent(mqttClient: MqttClient, topic: MqttTopics): String? {
//    println("Test 1")
//    var myMessage: String? = null
//    mqttClient.subscribe(topic.topicName) { _, message ->
//        myMessage = sendMyVerdammtenMqttMessage(message.payload.toString(Charsets.UTF_8))
//    }
////    mqttClient.subscribe(topic.topicName) { t,m-> myMessage=m}
//    Thread.sleep(2000)
//    mqttClient.disconnect()
//    return myMessage
//
////    return mqttClient.subscribe(topic.topicName)
//}


//fun main() {
//    val broker = createMqttSession()
//    println(getTopicContent(broker, MqttTopics.BmsSoc))

//    broker.subscribe("bms/soc") { topic, message ->
//        println("Message received: ${message.payload.toString(Charsets.UTF_8)}")
//    }
//}