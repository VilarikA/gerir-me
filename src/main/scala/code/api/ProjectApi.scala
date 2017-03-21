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


object ProjectApi extends RestHelper with ReportRest with net.liftweb.common.Logger {

		serve {
			case "project" :: Nil JsonGet _ =>{
				JsArray(Project1.findAllInCompany.map((a) =>{
					JsObj(
						("id", a.id.is),
						("name", a.name.is)
						)
				}))
			}
			case "project" :: "add_stakeholder" :: project :: Nil Post _ =>{
				val stakeholder = (S.param("bp_stakeholder") openOr "0").toLong
				val stakeholdertype = (S.param("stakeholdertype") openOr "0").toLong
				val projectObj = Project1.findByKey(project.toLong).get
				projectObj.addStakeholder(stakeholder, stakeholdertype);
				JInt(1)
			}
			case "project" :: "remove_stakeholder" :: project :: Nil Post _ =>{
				val stakeholder = (S.param("id") openOr "0").toLong
				val projectObj = Project1.findByKey(project.toLong).get
				projectObj.removeStakeholder(stakeholder);
				JInt(1)
			}			
			case "project" :: "add_paymentcondition" :: project :: Nil Post _ =>{
				val days = (S.param("days") openOr "0").toInt
				val percent = (S.param("percent") openOr "0").toFloat
				val paymentdate = Project.strOnlyDateToDate(S.param("paymentdate") openOr "")
				val value = (S.param("value") openOr "0").toFloat
				val obs = (S.param("obs") openOr "0").toString
				val projectObj = PaymentCondition.addPaymentCondition (project.toLong, days, paymentdate, percent, value, obs);
				JInt(1)
			}
			case "project" :: "remove_paymentcondition" :: id :: Nil Post _ =>{
				val projectObj = PaymentCondition.findByKey(id.toLong).get
				projectObj.delete_!

				JInt(1)
			}			
			case "api" :: "v2" :: "projectclass" :: Nil JsonGet _ =>{
			 	JsArray(ProjectClass.findAllInCompany.map((obj:ProjectClass) =>{
			 		obj.asJsToSelect
			 	}))
			}		
			case "api" :: "v2" :: "projectstage" :: Nil JsonGet _ =>{
			 	JsArray(ProjectStage.findAllInCompany.map((obj:ProjectStage) =>{
			 		obj.asJsToSelect
			 	}))
			}		
			case "api" :: "v2" :: "project" :: Nil JsonGet _ =>{
			 	JsArray(Project1.findAllInCompany.map((obj:Project1) =>{
			 		obj.asJsToSelect
			 	}))
			}		
		}
}