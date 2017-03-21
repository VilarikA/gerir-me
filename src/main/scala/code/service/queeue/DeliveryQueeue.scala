package code
package service

import code.model._
import org.apache.activemq.ActiveMQConnectionFactory

import javax.jms._

object DeliveryQueeue extends Queeue[PaymentProcessDTO] with net.liftweb.common.Logger{
    val queueName = "ebelle.delivery"
    def dequeeue(payment:PaymentProcessDTO){
        if(!payment.remove){
            DeliveryService.processPayment(payment)
        }else{
            info(payment.treatments.toString)
            DeliveryService.removePayment(payment, payment.treatments)
        }
    }

    def processPaymentEnqueeue(payment:Payment) = {
        this.enqueeue(PaymentProcessDTO(payment.id.is,false))
    }

    def removePaymentEnqueeue(payment:Payment, treatments:List[Long]=Nil) = {

        this.enqueeue(PaymentProcessDTO(payment.id.is, true, treatments))
    }

}