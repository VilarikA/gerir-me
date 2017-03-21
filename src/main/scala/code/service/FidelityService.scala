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

object FidelityService extends  net.liftweb.common.Logger {

	def processPayment(payment:Payment){
		//info("FidelityService:::processPayment")
		val treatmentDetailsWithPoints = payment.treatments.map(
											(t:Treatment) => t.details.toList
										).reduceLeft(_:::_)
										.filter(
											(td) => td.hasPointsOnBuy 
										)
		val hasPaymentOfDebitis = treatmentDetailsWithPoints.size > 0
		//info("FidelityService:::"+hasPaymentOfDebitis)
		if(hasPaymentOfDebitis){
			val customer = payment.customer.obj.get
			treatmentDetailsWithPoints.foreach((td) => {
				//info("FidelityService:::Register"+td.pointsOnBuy)
				customer.registerPoints(td.pointsOnBuy,  payment, td, "Comprando item com pontos!")
			})
		}
	}

	def removePayment(payment:Payment){
		val treatmentDetailsWithPoints = payment.treatments.map(
											(t:Treatment) => t.details.toList
										).reduceLeft(_:::_)
										.filter(
											(td) => td.hasPointsOnBuy 
										)
		val hasPaymentOfDebitis = treatmentDetailsWithPoints.size > 0
		if(hasPaymentOfDebitis){
			val customer = payment.customer.obj.get
			treatmentDetailsWithPoints.foreach((td) => {
				customer.registerPoints(td.pointsOnBuy*(-1.0),  payment, td, "Comprando item com pontos!")
			})
		}		
	}	
}