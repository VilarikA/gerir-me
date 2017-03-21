package code
package api

import code.model._
import code.util._
import code.service._
import code.actors._

import net.liftweb._
import mapper._
import common._
import http._
import rest._
import json._
import http.js._
import JE._
import net.liftweb.util.Helpers

import scala.xml._

import java.text.ParseException
import java.util.Date

import net.liftweb.json._
//implicit val formats = DefaultFormats // Brings in default date formats etc.


object ComissionApi extends RestHelper  with net.liftweb.common.Logger  {
	serve {

		case "comission" :: "process_start" :: Nil Post _ =>{
			for {
				dateStart <- S.param("start") ?~ "start parameter missing" ~> 400
				dateEnd <- S.param("end") ?~ "end parameter missing" ~> 400
			} yield {				
				var start = Project.strToDateOrToday(dateStart)
				var end = Project.strToDateOrToday(dateEnd)
				CommissionService.prepareCommisionToReprocess(start, end, AuthUtil.company)
				JInt(1)
			}			
		}
		case "comission" :: "category_start" :: Nil Post _ =>{
			AccountCategory.reorgCategory
			JInt(1)
			
		}
	}
}