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
import net.liftweb.mapper._ 

import scala.xml._

import java.text.ParseException
import java.util.Date
import java.util.Calendar

import net.liftweb.json._
import scalendar._
import Month._
import Day._
//implicit val formats = DefaultFormats // Brings in default date formats etc.

object CommandApi extends RestHelper with ReportRest with net.liftweb.common.Logger  {
	
	serve {
	
		case "command" :: "usersales" :: Nil Post _ => {
			try {
				var userId:Long = S.param("user").get.toLong;
				var password:String = S.param("password").get;

				//if ((password == "edoctus") && (userId == 0)) {
				if ((userId == 0)) {
					userId = AuthUtil.user.id.is
					password = AuthUtil.user.password
				}
				if (AuthUtil.user.isCommandPwd) {
					if (!User.loginCommand(userId.toLong, password)) {
						throw  new Exception("Erro no relatório \n\nSenha inválida!");
					}
				}

			    def str_unit:String = if (!AuthUtil.company.calendarShowDifUnit_?) {
			      " AND tr.unit = ? "
			      } else {
			      " AND (tr.unit = ? or 1 = 1) "
			    }

				val SQL = """
					select 
					to_char (tr.start_c, 'hh24:mi'),
					to_char (ted.arrivedat, 'hh24:mi'),
					bc.name, ba.short_name, ban.short_name as pet, 
					pr.name, td.amount, td.price, 
					tr.status, 
					trim (COALESCE (ted.obsLate, '') || ' ' || tr.obs || ' ' || td.obs), 
					'Aguardando a ' || to_char (now() - ted.arrivedat,'hh24:mi'),
					bc.id, td.id,
					(select defaultquiz from usergroup where id = bp.group_c),
					ba.id, ban.id, tr.status2
					from treatment tr 
					inner join business_pattern bc on bc.id = tr.customer
					inner join business_pattern bp on bp.id = tr.user_c
					inner join treatmentdetail td on td.treatment = tr.id
					left  join business_pattern ba on ba.id = td.auxiliar
					inner join product pr on pr.id = td.activity or pr.id = td.product
					left join treatedoctus ted on ted.treatment = tr.id
					left join tdepet tdp on tdp.treatmentDetail = td.id
					left join business_pattern ban on ban.id = tdp.animal
					where tr.company = ? and (tr.user_c = ? or td.auxiliar = ?)  and tr.dateevent = date (now()) 
					and tr.hasdetail = true and tr.status <> 5
					%s
					order by start_c, bc.name, pr.name
				"""
				toResponse(SQL.format(str_unit),List(AuthUtil.company.id.is, userId, userId, AuthUtil.unit.id.is)) 
			} catch {
			  case e: Exception => JString(e.getMessage)
			}			
		}
		case "command" :: "treataux" :: Nil Post _ => {
			try {
				var userId:Long = S.param("user").get.toLong;
				var password:String = S.param("password").get;

				//if ((password == "edoctus") && (userId == 0)) {
				if ((userId == 0)) {
					userId = AuthUtil.user.id.is
					password = AuthUtil.user.password
				}

				if (AuthUtil.user.isCommandPwd) {
					if (!User.loginCommand(userId.toLong, password)) {
						throw  new Exception("Erro no relatório \n\nSenha inválida!");
					}
				}

				val SQL = """
					select to_char (tr.start_c,'hh24:mi') , bc.name as cliente, bp.short_name as profissional, 
					ba.short_name as assistente, 
					ban.short_name as pet, 
					pr.short_name as prodserv, td.id, bc.id, bp.id, 
					ba.id, -- assitente
					ban.id -- animal
					from treatment tr
					inner join treatmentdetail td on td.treatment = tr.id
					inner join business_pattern bc on bc.id = tr.customer
					inner join business_pattern bp on bp.id = tr.user_c
					inner join product pr on pr.id = td.activity or pr.id = td.product and pr.productclass in (0,1)
					left join business_pattern ba on ba.id = td.auxiliar
					left join tdepet tdp on tdp.treatmentDetail = td.id
					left join business_pattern ban on ban.id = tdp.animal
					where tr.company = ? and tr.dateevent = date(now())
					and tr.status not in (5,4,8,1) -- pago deletado desmarcou faltou
					order by tr.start_c				"""
				toResponse(SQL,List(AuthUtil.company.id.is)) 
			} catch {
			  case e: Exception => JString(e.getMessage)
			}			
		}
		case "command" :: "getCustomers" :: Nil JsonGet _ =>{
			JsArray(Customer.findAllInCompany(
				BySql(" id in (select distinct tr.customer from treatment tr where tr.dateevent = date (now()) and tr.company = ? and tr.hasdetail = true and tr.status not in (5,4,8,1) ) ", // pago deletado desmarcou faltou
				IHaveValidatedThisSQL("datepayment","01-01-2012 00:00:00"), AuthUtil.company.id), OrderBy (Customer.name, Ascending)).map( (u) => {
				JsObj(("status","success"),("name",u.name.is),("id",u.id.is))
			}))
		}
		case "command" :: "add_command" :: Nil Post _ => {
			try { 
			  
				//def start = Project.strToDate( S.param("start") openOr "" )
				def start:String = S.param("start") openOr ""
				//def end = Project.strToDate( S.param("end") openOr "" )
				def end:String = S.param("end") openOr ""
				def obs = S.param("obs") openOr ""
				def activity = S.param("activity") openOr ""
				def product = S.param("product") openOr ""
				def password = S.param("password") openOr ""
				def price = S.param("price") openOr ""
				def amount = S.param("amount") openOr ""
				def animal = S.param("animal") openOr ""
				def offsale = S.param("offsale") openOr ""

				def userId:String = S.param("user") openOr "0"
				def customerId:String = S.param("customer") openOr "0"
				def auxiliarId:String = S.param("auxiliar") openOr "0"

				if (AuthUtil.user.isCommandPwd) {
					if (!User.loginCommand(userId.toLong, password)) {
						throw  new Exception("Senha inválida!");
					}
				}

				var tempt = TreatmentService.factoryTreatment("", customerId, userId, start, start, end,"0")
				if (activity.isEmpty || activity == "") {
					var prod = Product.findByKey(product.toLong).get
					var tempd1 = TreatmentService.addDetailTreatment(tempt.get.id, prod, animal.toLong, offsale.toLong)
					if (amount != "" && amount != "1") {
						//println ("vai amount ========= " + amount );
						tempd1.get.amount(amount.toDouble).price(tempd1.get.price*amount.toDouble).save
					}
					if (price != "") {
						tempd1.get.price(price.toDouble*amount.toDouble).save
					}
					if (obs != "") {
						tempd1.get.obs(obs).save
					}
				} else {
					var tempd = TreatmentService.addDetailTreatmentWithoutValidate(tempt.get.id, activity.toLong, 
						auxiliarId.toLong, animal.toLong, offsale.toLong)
					if (amount != "" && amount != "1") {
						tempd.get.amount(amount.toDouble).price(tempd.get.price*amount.toDouble).save
					}
					if (price != "" && price != "0") {
						tempd.get.price(price.toDouble*amount.toDouble).save
					}
					if (obs != "") {
						tempd.get.obs(obs).save
					}
				}
				if (end != "" && end.length > 11) {
					tempt.get.end(Project.strToDate(end)).save
				}
				if (obs != "") {
					tempt.get.obs(tempt.get.obs + " - " + obs).save
				}
				JInt(1)
			} catch {
			  case e: Exception => JString(e.getMessage)
			}			
		}
		case "command" :: "del_detail" :: Nil Post _ => {
			try { 
				def tdId:String = S.param("tdid") openOr "0"
				TreatmentService.deleteDetail(tdId.toLong)
				JInt(1)
			} catch {
			  case e: Exception => JString(e.getMessage)
			}			
		}

		case "command" :: "setaux" :: Nil Post _ => {
			//
	        // usado tambem na Agenda além de aqui na comnda e no caixa
	        //
			try { 
				def userId:String = S.param("user") openOr "0"
				def tdId:String = S.param("tdid") openOr "0"
				def command:Boolean = S.param("command") == "1"
				val td = TreatmentDetail.findByKey (tdId.toLong)
				val tdaux = td.get.auxiliar;
				if (tdaux == userId.toLong && userId.toLong != 0) {
					if (command) {
						throw new RuntimeException ("Você já é o assistente neste serviço!")
					} else {
						// agenda
						val bp = User.findByKey (tdaux)
						val bpname = bp.get.short_name;
						throw new RuntimeException (bpname + " já é o assistente neste serviço!")
					}
				} else if (tdaux == 0 || tdaux == null) {
					td.get.auxiliar(userId.toLong).save
				} else {
					if (command) {
						val bp = User.findByKey (tdaux)
						val bpname = bp.get.short_name;
						throw new RuntimeException (bpname + " já é assistente neste serviço!")
					} else {
						// agenda deixa excluir e alterar assitente setado
						td.get.auxiliar(userId.toLong).save
					}
				}
				JInt(1)
			} catch {
			  case e: Exception => JString(e.getMessage)
			}			
		}
		case "command" :: "delaux" :: Nil Post _ => {
			try { 
				def userId:String = S.param("user") openOr "0"
				def tdId:String = S.param("tdid") openOr "0"
				val td = TreatmentDetail.findByKey (tdId.toLong)
				val tdaux = td.get.auxiliar;
				if (tdaux == userId.toLong) {
					td.get.auxiliar(0l).save
				} else if (tdaux == 0 || tdaux == null) {
					throw new RuntimeException ("Não há assistente neste serviço!")
				} else {
					val bp = User.findByKey (tdaux)
					val bpname = bp.get.short_name;
					throw new RuntimeException (bpname + " é assistente neste serviço!")
				}
				JInt(1)
			} catch {
			  case e: Exception => JString(e.getMessage)
			}			
		}
		case "command" :: "setpet" :: Nil Post _ => {
			//
	        // usado tambem na Agenda além de aqui na comnda e no caixa
	        //
			try { 
				def petId:String = S.param("animal") openOr "0"
				def tdId:String = S.param("tdid") openOr "0"
				def command:Boolean = S.param("command") == "1"
				val td = TreatmentDetail.findByKey (tdId.toLong).get
						//detail.getTdEpet.animal(animal).save;
				val tdpet = td.getTdEpet.animal;
				if (tdpet == petId.toLong && petId.toLong != 0) {
					// agenda
					val bp = User.findByKey (tdpet)
					val bpname = bp.get.short_name;
					throw new RuntimeException (bpname + " já é o pet neste serviço!")
				} else if (tdpet == 0 || tdpet == null) {
					td.getTdEpet.animal(petId.toLong).save;
				} else {
					// agenda deixa excluir e alterar pet setado
					td.getTdEpet.animal(petId.toLong).save;
				}
				JInt(1)
			} catch {
			  case e: Exception => JString(e.getMessage)
			}			
		}
	}
}
