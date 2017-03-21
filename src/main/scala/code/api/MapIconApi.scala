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

object MapIconApi extends RestHelper  with net.liftweb.common.Logger  {

	serve {
		case "mapicon" :: Nil Get _ =>{
			JsArray(
				MapIcon.findAllInCompanyOrDefaultCompanyMapicon.map((mapIcon)=>{
					JsObj(
						("name",mapIcon.short_name.is), 
						("id",mapIcon.id.is),
						("long_name",mapIcon.name.is),
						("image",mapIcon.iconPath.is)
					)
				}
			)
			)
		}
	}
}