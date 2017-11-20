package code.service.events
 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 
import net.liftweb.common.{Box,Full,Empty}
import code.util._
import code.comet._
import code.service._
import code.model._

object EventAggregator  extends  net.liftweb.common.Logger {
	def onPaymentSuccess(event:PaymentSuccessMessage){
		//info("CommisionQueeue :: - :: ")
		CommisionQueeue.processPaymentEnqueeue(event.payment)
		//info("DeliveryQueeue :: - :: ")
		DeliveryQueeue.processPaymentEnqueeue(event.payment)
		//info("DebitService :: - :: ")
		DebitService.processPayment(event.payment)
		MonthlyTreatmentService.processPayment(event.payment)
		FidelityService.processPayment(event.payment)
		//info("TratmentServer :: - :: ")
		TratmentServer ! TreatmentMessage("PaymentTreatment", event.payment.datePayment)
	}

	def onPaymentRemoved(event:PaymentRemovedMessage){
		event.payment.treatments.foreach(_.status(Treatment.Open).status2(Treatment.Open).payment(0).saveWithoutValidate)
		FidelityService.removePayment(event.payment)
		MonthlyTreatmentService.removePayment(event.payment)
		DebitService.removePayment(event.payment)
		CommisionQueeue.removePaymentEnqueeue(event.payment)
		DeliveryQueeue.removePaymentEnqueeue(event.payment, event.treatments)
		TratmentServer ! TreatmentMessage("PaymentTreatmentRemoved", event.payment.datePayment)
	}

	def onFindCashierOpen {
		Cashier.singleCashierUnitProcess
	}
}

case class PaymentSuccessMessage(payment:Payment)
case class PaymentRemovedMessage(payment:Payment, treatments:List[Long] = Nil)