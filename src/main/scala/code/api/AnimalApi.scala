package code
package api

import code.model._
import code.util._
import code.actors._
import code.comet._
import code.service._

import net.liftweb._
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
import java.util.Calendar

import net.liftweb.json._
import scalendar._
import Month._
import Day._
//implicit val formats = DefaultFormats // Brings in default date formats etc.

object AnimalApi extends RestHelper with net.liftweb.common.Logger  {
	
	serve {
		
		case  "animal" :: "animal" :: "add" :: Nil Post _ => {
			try{
				def name = S.param("name") openOr ""
				def obs = S.param("obs") openOr ""
				def bp_manager_str = S.param("bp_manager") openOr "0"
				def bp_manager = if(bp_manager_str == ""){
					"0"
				}else{
					bp_manager_str
				}
				def bp_indicatedby_str = S.param("bp_indicatedby") openOr "0"
				def bp_indicatedby = if(bp_indicatedby_str == ""){
					"0"
				}else{
					bp_indicatedby_str
				}
				val animal = AnimalPartner.create.
				name(name).
				obs(obs).
				bp_manager(bp_manager.toLong).
				bp_indicatedby(bp_indicatedby.toLong).
				species(1). // cao
				company(AuthUtil.company)
				animal.save
				JInt(animal.id.is)
			}catch{
				case e:RuntimeException => {
					JString(e.getMessage)
				}
				case _ =>{
					JString("Erro desconhecido, ao cadastrar pet!")
				}
			}
		}
	}
}
