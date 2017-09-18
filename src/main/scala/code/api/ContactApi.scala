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

import net.liftweb.json._
//implicit val formats = DefaultFormats // Brings in default date formats etc.


object ContactApi extends RestHelper with ReportRest with net.liftweb.common.Logger  {
		serve {

			case "contact" :: "conciliate" :: Nil Post _ => {
				val ids = S.param("ids").get
				ids.split(",").map(_.toLong).map((l:Long) => {
					val ac = Contact.findByKey(l).get
					ac.makeAsCustomer
				})
				JInt(1)
			}

			case "contact" :: "conciliateTotal" :: origin :: Nil JsonGet _ => {
				try{
				    Contact.findAllInCompany (
				      By (Contact.origin, origin)).map ((ac) => {
				        ac.makeAsCustomer;
				    })
					JInt(1)
				} catch {
					case e:Exception => JString(e.getMessage)
				}
			}

		}
}
