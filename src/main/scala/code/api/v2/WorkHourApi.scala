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


object WorkHourApi extends RestHelper with ReportRest with net.liftweb.common.Logger {

		serve {

			case "api" :: "user" :: "workhour" :: userId :: Nil JsonGet _ =>{
			 	JsArray(WorkHouer.findAll(
			 		By(WorkHouer.user, userId.toLong), 
			 		By(WorkHouer.unit, AuthUtil.unit.id.toLong),
			 		OrderBy(WorkHouer.daynumber, Ascending)).map((obj:WorkHouer) =>{
			 		obj.asJs
			 	}))
			}
			case "api" :: "user" :: "workhour" :: userId ::  id :: Nil JsonGet _ =>{
			 	WorkHouer.findByKey(id.toLong).get.asJs
			}
			case "api" :: "user" :: "workhour" :: userId ::  id :: "delete":: Nil JsonGet _ =>{
			 	WorkHouer.findByKey(id.toLong).get.delete_!
			 	JInt(1)
			}			
			case "api" :: "user" :: "workhour" :: userId :: id :: Nil JsonPost json -> _ =>{
			 	WorkHouer.findByKey(id.toLong).get.updateFromJson(json.asInstanceOf[JsonAST.JObject])
			 	JInt(1)
			}

			case "api" :: "user" :: "workhour" :: userId :: Nil JsonPost json -> _ =>{
				WorkHouer.createFromJson(json.asInstanceOf[net.liftweb.json.JsonAST.JObject]).save
				JString("")
			}			
		}

}
