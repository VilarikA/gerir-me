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


object MediaApi extends RestHelper{

		serve {
			case "media" :: "list" :: Nil JsonGet _ =>{
				JsArray(Media.findAllInCompany(OrderBy(Media.id, Descending)).map((m)=>{
					JsObj(
							("id",m.id.is),
							("url",m.logo_web),
							("thumb",m.logo_web)
						)
				}))
			}
			case "media" :: "delete" :: id :: Nil JsonGet _ =>{
				Media.findByKey(id.toLong).get.delete_!
				JInt(1)
			}			
		}
}