package code
package service

import code.model._
import code.util._
import org.apache.activemq.ActiveMQConnectionFactory

import javax.jms._

object BusinessPatternAjustsQueeue extends Queeue[BusinessPatternQueeueDto]{
    override val queueName = "ebelle.business_pattern_ajusts"
    def dequeeue(n:BusinessPatternQueeueDto){
    	val customer = Customer.findByKey(n.objId).get
        customer.name(customer.name.is+" ").save
    }
}