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


object OffSaleCrudApi extends RestHelper with ReportRest with net.liftweb.common.Logger {

		serve {

			case "api" :: "offsale" :: Nil JsonGet _ =>{
			 	JsArray(OffSale.findAll.map((obj:OffSale) =>{
			 		obj.asJs
			 	}))
			}
			case "api" :: "offsale" :: id :: Nil JsonGet _ =>{
			 	OffSale.findByKey(id.toLong).get.asJs
			}			
			case "api" :: "offsale" :: id :: Nil JsonPost json -> _ =>{
			 	OffSale.findByKey(id.toLong).get.updateFromJson(json.asInstanceOf[JsonAST.JObject])
			 	JInt(1)
			}

			case "api" :: "offsale" :: Nil JsonPost json -> _ =>{
				OffSale.createFromJson(json.asInstanceOf[net.liftweb.json.JsonAST.JObject]).save
				JString(OffSale.createFromJson(json.asInstanceOf[net.liftweb.json.JsonAST.JObject]).name.is)
			}			
		}

}
