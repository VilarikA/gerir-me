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


object CostCenterApi extends  RestHelper with ReportRest with net.liftweb.common.Logger {
		serve {

			case "api" :: "v2" :: "costcenter" :: Nil JsonGet _ =>{
			 	JsArray(CostCenter.findAllInCompany(OrderBy(CostCenter.name, Ascending)).map((obj:CostCenter) =>{
			 		obj.asJsToSelect
			 	}))
			}		
		}

}
