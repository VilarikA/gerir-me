package code
package service

import code.model._

import javax.jms._

import org.apache.activemq.ActiveMQConnectionFactory


case class UserSpeedServiceConsumer(brokerUrl:String,queueName:String) extends MessageListener with net.liftweb.common.Logger {
    def factory = new ActiveMQConnectionFactory(brokerUrl)
    val connection = factory.createConnection()
    connection.start()
    val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
    val destination = session.createQueue(queueName)
    val consumer = session.createConsumer(destination)
    consumer.setMessageListener(this)
    
    def onMessage(message:Message):Unit = {
        if (message.isInstanceOf[ObjectMessage]) {
            val objMessage = message.asInstanceOf[ObjectMessage]
            val speed = objMessage.getObject().asInstanceOf[SpeedRequest]
            speed.carObj.updateLocation(speed.lat.toString,speed.lng.toString,speed.distance)
        } else {
            info("Oops, not a object message")
        }
    }
}