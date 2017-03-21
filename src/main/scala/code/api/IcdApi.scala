package code
package api

import code.model._
import code.util._
import code.service._

import net.liftweb._
import common._
import http._
import rest._
import net.liftweb.mapper._
import json._
import http.js._
import JE._
import net.liftweb.util.Helpers

import scala.xml._

import java.text.ParseException
import java.util._

import net.liftweb.json._
//implicit val formats = DefaultFormats // Brings in default date formats etc.


object IcdApi extends RestHelper {
		def id = S.param("id") match {
			case Full(s) if(s != "") => {
				"p.id = "+s
			}
			case _ => {
				"1 =1"
			}
		}
		def nameStr =  BusinessRulesUtil.clearString(S.param("name") openOr "")
		def name = S.param("name") match {
			case Full(s) if(s != "") => {
				Like(Icd.search_name,"%"+BusinessRulesUtil.clearString(s)+"%")
			}
			case _ => {
				BySql[code.model.Icd]("1 =1",IHaveValidatedThisSQL("",""))
			}		
		}

	serve {
		case "icd" :: "icd_search" :: Nil JsonGet _ =>{
				def curPage = S.param("page") match {
								case Full(s) if(s != "") => {
									s.toInt
								}
								case _ => {
									0
								}		
							}						
			JsArray(Icd.findAllForSearch("%"+nameStr+"%", 
				curPage).map(asJson))
		}
		case "icd" :: "icd_search" :: Nil Post _ =>{
			JsArray(Icd.findAllForSearch("%"+nameStr+"%",0).map(asJson))
		}
	}

	def asJson (p:Icd):JsObj = JsObj(
					("status","success"),
					("name",p.name.is),
					("id",p.id.is)
				);
				def asJson (p:IcdSearch):JsObj = JsObj(
					("status","success"),
					("name",p.name),
					("id",p.id)
				);
}
