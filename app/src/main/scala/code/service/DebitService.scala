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

object DebitService extends  net.liftweb.common.Logger {

	def processPayment(payment:Payment){
		val debits = payment.treatments.map((t:Treatment) => t.details.toList).reduceLeft(_:::_).filter((td) => td.product.obj match {
			case Full(p) => p.productClass.is == ProductType.Types.PreviousDebts || p.productClass.is == ProductType.Types.CustomerCredits
			case _ => false
			}
		);
		val hasPaymentOfDebitis = debits.size > 0
		if(hasPaymentOfDebitis){
			payment.customer.obj.get.registerDebit(debits(0).price.is.toDouble, debits(0).treatment.obj.get, debits(0) , payment, "Pagando conta cliente!")
		}
	}

	def removePayment(payment:Payment){
		val treatments = payment.treatments.map((t) => t.details.toList)
		if(!treatments.isEmpty){
			val debits = treatments.reduceLeft(_:::_).filter((td) => td.product.obj match {
					case Full(p) => p.productClass.is == ProductType.Types.PreviousDebts  || p.productClass.is == ProductType.Types.CustomerCredits
					case _ => false
				}
			);
			val hasPaymentOfDebitis = debits.size > 0
			if(hasPaymentOfDebitis){
				payment.customer.obj.get.registerDebit(debits(0).price.is.toDouble*(-1), debits(0).treatment.obj.get, debits(0), payment, "Excluindo Pagamento")
			}
		}
	}	
}