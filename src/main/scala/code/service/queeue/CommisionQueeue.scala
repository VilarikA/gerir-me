package code
package service

import code.model._
import org.apache.activemq.ActiveMQConnectionFactory

import javax.jms._

object CommisionQueeue extends Queeue[PaymentProcessDTO]{
    override val queueName = "ebelle.commission_customer_account_credit"
    def dequeeue(payment:PaymentProcessDTO){
        if(!payment.remove){
            //info("Processig Payment: "+payment.paymentId)
            CommissionService.processPayment(payment)
        }else{
            CommissionService.removePayment(payment)
        }        
    }
    def processPaymentEnqueeue(payment:Payment) = {
        this.enqueeue(PaymentProcessDTO(payment.id.is,false))
    }

    def removePaymentEnqueeue(payment:Payment) = {
        this.enqueeue(PaymentProcessDTO(payment.id.is,true))
    }
}