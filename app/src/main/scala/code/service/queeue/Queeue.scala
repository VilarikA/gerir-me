package code
package service

import code.model._
import org.apache.activemq.ActiveMQConnectionFactory

import javax.jms._
object QueeueManager{
    val brokerUrl = "tcp://localhost:61616"
    val factory:ConnectionFactory = new ActiveMQConnectionFactory(brokerUrl)
    val connection = factory.createConnection()
    connection.start()
    lazy val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
}
trait Queeue[T <: java.io.Serializable] extends MessageListener with net.liftweb.common.Logger {
    val queueName:String
    lazy val destination = QueeueManager.session.createQueue(queueName)
    def enqueeue(obj:T){
        val producer = QueeueManager.session.createProducer(destination)
        producer.send(QueeueManager.session.createObjectMessage(obj))
    }
    def dequeeue(obj:T)

    def onMessage(message:Message):Unit = {
        if (message.isInstanceOf[ObjectMessage]) {
            val objMessage = message.asInstanceOf[ObjectMessage]
            val obj = objMessage.getObject().asInstanceOf[T]
            dequeeue(obj)
        } else {
            info(" Oops, %s recive one message not a object message".format(queueName))
        }
    }
    def start {
        lazy val consumer = QueeueManager.session.createConsumer(destination)
        consumer.setMessageListener(this)        
    }

 }