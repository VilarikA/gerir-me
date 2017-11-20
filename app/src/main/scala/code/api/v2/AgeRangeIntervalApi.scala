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


object AgeRangeIntervalApi extends  RestHelper with ReportRest with net.liftweb.common.Logger {
		def agerangeId = S.param("agerangeId").get.toLong		
		serve {
			case "api" :: "v2" :: "agerangeinterval" :: Nil Post _ => {
				for {
					agerange <- S.param("agerange") ?~ "agerange parameter missing" ~> 400
					obs <- S.param("obs") ?~ "obs parameter missing" ~> 400
					startmonths <- S.param("startmonths") ?~ "startmonths parameter missing" ~> 400
					endmonths <- S.param("endmonths") ?~ "endmonths parameter missing" ~> 400
				} yield {
					JBool(AgeRangeInterval.createInCompany.agerange(agerange.toLong).name(obs)
						.startmonths(startmonths.toInt).endmonths(endmonths.toInt).obs(obs).save)
				}
			}
			case  "api" :: "v2" :: "agerangeinterval" :: id :: Nil Delete _ => {
				JBool(AgeRangeInterval.findByKey(id.toLong).get.delete_!)
			}
			case  "api" :: "v2" :: "agerangeinterval" :: "list" :: Nil Post _ => {
			    lazy val agerangeinterval_query = """
			        select name, startmonths, startmonths/12, endmonths, endmonths/12, id from agerangeinterval where  company=? and agerange =?
			        order by startmonths
			    """
				toResponse(agerangeinterval_query,List(AuthUtil.company.id.is, agerangeId)) //agerangeId.toLong))
			}
		}

}
