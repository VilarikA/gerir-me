package code
package service

import code.model._
import org.apache.activemq.ActiveMQConnectionFactory

import javax.jms._

object NotificationQueeue extends Queeue[NotificationDto]{
    override val queueName = "ebelle.notification"
    def dequeeue(n:NotificationDto){
        Treatment.findByKey(n.objId).get.sendToFacebook
    }
}
case class NotificationDto(objId:Long)