package code
package snippet

import net.liftweb._
import http._
import code.util._
import code.service._
import model._
import http.js._
import JE._
import JsCmds._
import net.liftweb.util._
import net.liftweb.common._
import Helpers._
import scala.xml.{ NodeSeq, Text }
import java.util.Date

object  ReportCommission{

	def strToDate(s:String) = try{
			Project.strToDate(s+" 00:00")
			}catch{ 
				case _ => new Date(1l)
	}

	def startDate = S.param("startDate") match {
		case Full(s) if(s != "") => strToDate(s)
		case _ => new Date();
	}
	
	def endDate =  S.param("endDate") match {
		case Full(s) if(s != "") => strToDate(s)
		case _ => new Date();
	}
	
	def user = 	S.param("user") match {
		case Full(s) if(s != "") => User.findByKey(s.toLong).get
		case _ => User.create;
	}

	def paymentsFiltered = { 
		PaymentService.paymentsBetweenByUser(startDate,endDate,user).filter(_.treatments.size > 0)
	}

	def report(xhtml:NodeSeq):NodeSeq = {
		paymentsFiltered.flatMap(p => {
				bind("f", xhtml,"command" -> Text(p.command),
								"date" -> Text(p.date.getTime.toString),
								"customer_cod" -> Text(p.customer.id.is.toString),
								"customer_name" -> Text(p.customer.name.is),
								"treatments" -> tableTreatments(p.treatments),
								"payments" -> tablePayments(p.payments,p)
					)
				}
			)
	}

	def tablePayments(payments:List[PaymentDetail],payment:PaymentForReport) = {
		val totalForPayToUser = payment.totalForPayToUser
		<table>
	        <tbody>
	        {
	        	payments.map((p) => {
		          <tr>
		            <td class="span3">{p.typePaymentTranslated}</td>
		            <td class="span2"></td>
		            <td class="lift:SecuritySnippet.isShowSalesToUser translate_money span2">{p.value.is}</td>
					<td class="translate_money span2">{(totalForPayToUser * p.percentInTotal)/100.00}</td>
		          </tr>
	      		})
	        }
	        </tbody>
	      </table> 
  	}


	def tableTreatments(treatments:List[Treatment]):NodeSeq ={
      <table>
        <tbody>
        {
         treatments.map((t) => t.details.toList).foldLeft(List[TreatmentDetail]())(_:::_).map((a)=>{
	          <tr>
	            <td class="span3">{a.nameActivity}</td>
	            <td class="translate_money span2">{a.amount.is}</td>
	            <td class="lift:SecuritySnippet.isShowSalesToUser translate_money span2">{a.price.is}</td>
	            <td class="translate_money span2">{a.valueToUser}</td>
	          </tr>
	       })
          }
        </tbody>
      </table>   		
	}	
}