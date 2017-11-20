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


object UserApi extends  RestHelper with ReportRest with net.liftweb.common.Logger {
		serve {

			case "api" :: "v2" :: "user" :: Nil JsonGet _ =>{
			 	JsArray(User.findAllInCompany.map((obj:User) =>{
			 		obj.asJs
			 	}))
			}
			case "api" :: "v2" :: "user" ::  id :: Nil JsonGet _ =>{
			 	User.findByKey(id.toLong).get.asJs
			}
			case "api" :: "v2" :: "user" ::  id :: "delete":: Nil JsonGet _ =>{
			 	User.findByKey(id.toLong).get.delete_!
			 	JInt(1)
			}			
			case "api" :: "v2" :: "user" :: id :: Nil JsonPost json -> _ =>{
				try{
					User.findByKey(id.toLong).get.updateFromJson(json.asInstanceOf[JsonAST.JObject])
		 			JInt(1)
				}catch{
					case e:RuntimeException => (ParamFailure(e.getMessage, 500)) : Box[JValue]
					case _ => JString("Error")
				}

			}

			case "api" :: "v2" :: "user" :: Nil JsonPost json -> _ =>{
				try{
					val user = User.createFromJson(json.asInstanceOf[net.liftweb.json.JsonAST.JObject])
					user.save
					JsObj(("id", user.asInstanceOf[User].id.is))
				}catch{
					case e:RuntimeException => (ParamFailure(e.getMessage, 500)) : Box[JValue]
					case _ => JString("Error")
				}

			}			
		}

}
