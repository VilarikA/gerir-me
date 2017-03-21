package code
package service

import net.liftweb._
import mapper._ 
import code.util._
import code.comet._
import model._
import net.liftweb.util._
import http.js._
import JE._
import net.liftweb.common._
//import InventoryMovement._
import java.util.Date
import java.util.Calendar

object DeliveryService extends  net.liftweb.common.Logger   {
	def processPayment(payment:PaymentProcessDTO){
		DB.use(DefaultConnectionIdentifier) {
			conn =>
			val paymentObj = payment.payment
			val details = paymentObj.detailTreaments
			var deliveries = paymentObj.deliveryDetails
			if(deliveries.size > 0){
				deliveries.foreach((deliveryHeader) => {
					val detailFilter = details.filter(_.for_delivery_?.is).filter(_.parentBom.is == deliveryHeader.product.is)
					if(detailFilter.size > 0){
						val delivery = DeliveryControl.create.company(paymentObj.company).product(deliveryHeader.product).payment(paymentObj).customer(deliveryHeader.customer).efetivedate(paymentObj.datePayment).treatment(deliveryHeader.treatment)
						delivery.save
						detailFilter.foreach((td) => createDelivery(td, paymentObj, td.customer,delivery))
					}
				})
			}
		}
	}
	def removePayment(payment:PaymentProcessDTO, treatments:List[Long]=Nil) = {
		DB.use(DefaultConnectionIdentifier) {
			conn =>
			treatments match {
                case (ts) if(ts.size >0 ) => {
                    ts.foreach((t:Long) => { Treatment.findByKey(t).get.removeDeliveries })
                }
                case _ => {

                }
            }
		}
	}

	def createDelivery(treatmentDetail:TreatmentDetail, payment:Payment, customer:Customer,delivery:DeliveryControl) = {
		val product = treatmentDetail.product.obj.get
		delivery.treatment(treatmentDetail.treatment)
		for(i <- 0 to treatmentDetail.amount.is.toInt-1) {
			DeliveryDetail.create.company(treatmentDetail.company).customer(customer).delivery(delivery).price(treatmentDetail.unit_price).product(product).save
		}
	}

	def remove(payment:PaymentProcessDTO){
		//payment.commissions.foreach(_.delete_!)
	}
}