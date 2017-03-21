package code
package service

import code.model._
import org.apache.activemq.ActiveMQConnectionFactory

import javax.jms._

case class UserSpeedService(factory:ConnectionFactory, queueName:String) {
    lazy val connection = factory.createConnection()
    connection.start()
    val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
    val destination = session.createQueue(queueName)
    val producer = session.createProducer(destination)
    def sendMessage(m:Message):Any = {
        producer.send(m)
        close
    }
    def send(speed:SpeedRequest):Any = sendMessage (session.createObjectMessage(speed))
    def close() = {
        if (connection != null) { connection.close() }
    }
}

object UserSpeedService {
  val queueName = "ebelle.seepd"
  val brokerUrl = "tcp://transitobh.com.br:61616"
  
  lazy val factory:ConnectionFactory = new ActiveMQConnectionFactory(brokerUrl)
  def save(speed:SpeedRequest) = {
    val producer = UserSpeedService(factory, queueName)
    producer.send(speed)
    producer.close
 }
}

case class SpeedRequest(speed:Double,lat:Double,lng:Double,hprecision:Double,user:String,company:Int,password:String,distance:Double){
    lazy val authStatus = User.login(user,password,companyObj)
    def auth_? = authStatus.status
    def carObj = authStatus.user
    def companyObj = Company.findByKey(company.toLong).get

}