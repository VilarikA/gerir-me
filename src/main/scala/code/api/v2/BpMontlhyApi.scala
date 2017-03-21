package code
package api

import code.model._
import code.util._
import code.service._
import code.actors._

import net.liftweb._
import common._
import http._
import rest._
import json._
import http.js._
import JE._
import net.liftweb.util.Helpers
import net.liftweb.mapper._ 
import scala.xml._

import java.text.ParseException
import java.util.Date
import java.util.Calendar

import net.liftweb.json._
//implicit val formats = DefaultFormats // Brings in default date formats etc.


object BpMonthlyApi extends  RestHelper with ReportRest with net.liftweb.common.Logger {
		serve {
			case "api" :: "v2" :: "bpmonthly" :: Nil Post _ => {
				def getFirstDateOfCurrentMonth() = {
				  val cal = Calendar.getInstance()
				  cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH))
				  cal.getTime()
				}
				def getLastDateOfCurrentMonth = {
				  val cal = Calendar.getInstance()
				  cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
				  cal.getTime()
				}
				for {
					customer <- S.param("customer") ?~ "customer parameter missing" ~> 400
					obs <- S.param("obs") ?~ "obs parameter missing" ~> 400
					product <- S.param("product") ?~ "type parameter missing" ~> 400

				} yield {
					JBool(BpMonthly.createInCompany.business_pattern(customer.toLong).obs(obs).product(product.toInt)
						.startAt(getFirstDateOfCurrentMonth).endAt(getLastDateOfCurrentMonth).save)
				}
			}
			case  "api" :: "v2" :: "bpmonthly" :: id :: Nil Delete _ => {
				JBool(BpMonthly.findByKey(id.toLong).get.delete_!)
			}
		}

}
