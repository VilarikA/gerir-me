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


object RelationshipApi extends  RestHelper with ReportRest with net.liftweb.common.Logger {
		serve {
			case "customer_api" :: "relationship" :: Nil Post _ => {
				for {
					business_pattern <- S.param("customer") ?~ "customer parameter missing" ~> 400
					obs <- S.param("obs") ?~ "obs parameter missing" ~> 400
					relationship <- S.param("bank") ?~ "bank parameter missing" ~> 400
				} yield {
					JBool(BpRelationship.createInCompany.business_pattern(business_pattern.toLong).obs(obs).save)
				}
			}
			case "customer_api" :: "relationship" :: id :: Nil Delete _ => {
				JBool(BpRelationship.findByKey(id.toLong).get.delete_!)
			}			
			case "api" :: "v2" :: "relationshiptype" :: Nil JsonGet _ =>{
			 	JsArray(RelationshipType.
			 		findAllInCompanyOrDefaultCompany(OrderBy(RelationshipType.name, Ascending)).map((obj:RelationshipType) =>{
			 		obj.asJsToSelect
			 	}))
			}		
		}

}
