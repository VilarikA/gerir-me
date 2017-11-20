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

object MonthlyTreatmentService extends  net.liftweb.common.Logger {

	def today = new Date();
    def next_month (bpmCount:Int) = {
      val cal = Calendar.getInstance()
      cal.setTime(today); 
      cal.add(java.util.Calendar.MONTH, bpmCount);
      cal.add(java.util.Calendar.DATE, -1);
      cal.getTime()
    }

	def getFirstDateOfCurrentMonth() = {
	  val cal = Calendar.getInstance()
	  cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH))
	  cal.getTime()
	}
	def getLastDateOfCurrentMonth (bpmCount:Int) = {
	  val cal = Calendar.getInstance()
	  if (bpmCount > 1) {
	      cal.add(java.util.Calendar.MONTH, (bpmCount - 1));
	  }
	  cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
	  cal.getTime()
	}
	def registerMonthly(payment:Payment) = {
		//info("MonthlyTreatmentService:::processPayment")
		val treatmentDetailsWithMonthly = payment.treatments.map(
											(t:Treatment) => t.details.toList
										).reduceLeft(_:::_)
										.filter(
											(td) => td.isAMonthlyService 
										)
		val wasPaid = payment.details.filter(
											(t:PaymentDetail) => {
												!t.bpmonthly_?
											}).size > 0
		val hasTreatmentDetailsWithMonthly = treatmentDetailsWithMonthly.size > 0
		//info("MonthlyTreatmentService:::"+hasTreatmentDetailsWithMonthly)
		if(hasTreatmentDetailsWithMonthly && wasPaid){
			val customer = payment.customer.obj.get
			treatmentDetailsWithMonthly.foreach((td) => {
				//info("MonthlyTreatmentService:::Register"+td.pointsOnBuy)
				val t = td.treatment.obj.get
				val user = t.user.obj.get
				val unit = t.unit.obj.get
				val bpmCount = td.productBase.bpmCount
				if (AuthUtil.company.bpmStartDay == 0) {
					BpMonthly.registerActivityBpMonthly(customer, user, unit, td.productBase, td.price.is.toDouble, td.price.is.toDouble, today, next_month (bpmCount))
				} else {
					BpMonthly.registerActivityBpMonthly(customer, user, unit, td.productBase, td.price.is.toDouble, td.price.is.toDouble, getFirstDateOfCurrentMonth, getLastDateOfCurrentMonth (bpmCount))
				}
			})
		}		
	}

	def checkMonthly(payment:Payment) = {
		val customer = payment.customer.obj.get
		val paymentDate = payment.datePayment
		val isUsingMonthly = payment.details.filter(
			(t:PaymentDetail) => {
				t.bpmonthly_?
			}).size > 0
		if(isUsingMonthly){
			val treatmentDetails = payment.treatments.map(
					(t:Treatment) => t.details.toList
				).reduceLeft(_:::_)
			treatmentDetails.foreach( (td) => {
				try{
					BpMonthly.monthlyByProduct(td.productBase, customer, paymentDate) //getFirstDateOfCurrentMonth)
				}catch {
					case _ => {
						throw PaymentMonthlyNotEnough("Cliente não possui mensalidade do serviço/produto : %s".format(td.productBase.name.is))
					}
				}
			})
		}
	}

	def processPayment(payment:Payment){
		this.checkMonthly(payment)
		this.registerMonthly(payment)
	}

	def removePayment(payment:Payment){
		//info("MonthlyTreatmentService:::processPayment")
		val treatmentDetailsWithMonthly = payment.treatments.map(
											(t:Treatment) => t.details.toList
										).reduceLeft(_:::_)
										.filter(
											(td) => td.isAMonthlyService 
										)
		val wasPaid = payment.details.filter(
											(t:PaymentDetail) => {
												!t.bpmonthly_?
											}).size > 0
		val hasTreatmentDetailsWithMonthly = treatmentDetailsWithMonthly.size > 0
		//info("MonthlyTreatmentService:::"+hasTreatmentDetailsWithMonthly)
		if(hasTreatmentDetailsWithMonthly && wasPaid){
			val customer = payment.customer.obj.get
			val paymentDate = payment.datePayment
			treatmentDetailsWithMonthly.foreach((td) => {
				if (BpMonthly.countBpMonthlyByProduct(td.productBase, customer, paymentDate)>0) {
					BpMonthly
					.monthlyByProduct(td.productBase, customer, paymentDate) //getFirstDateOfCurrentMonth)
					.delete_!
				}
			})

		}		
	}	
}