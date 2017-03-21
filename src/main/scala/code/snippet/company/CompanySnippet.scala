
package code 
package snippet 

import net.liftweb._
import http._
import model._
import code.util._
import actors._
import http.js._
import net.liftweb.util._
import net.liftweb.common._
import Helpers._
import scala.xml.{NodeSeq, Text}

object CompanySnippet{
	def render(in: NodeSeq):NodeSeq  = {
		for {
			r <- S.request if r.post_?
				name <- S.param("name")
				phone <- S.param("phone")
				contact <- S.param("contact")
				email  <- S.param("email")

		}{
			var company = AuthUtil ? match {
				case true => AuthUtil company
				case _ => Company.create
			}
//			println (S.hostName + "++++++++++++++++++============================")
			  def product1:String = if (S.hostName.contains ("gerir")) {
					company.appType(2)
			      " gerir-me "
			    }else if (S.hostName.contains ("egrex")) {
					company.appType(5)
			      " e-grex "
			    }else if (S.hostName.contains ("esmile")) {
					company.appType(3)
			      " e-smile "
			    }else if (S.hostName.contains ("edoctus")) {
					company.appType(4)
					company.calendarInterval(30)
			      " e-doctus "
			    }else if (S.hostName.contains ("efisio") || S.hostName.contains ("ephysio")) {
			    	company.calendarStart(7)
			    	company.calendarEnd(21)
					company.appType(6)
					company.calendarInterval(60)
			      " e-physio "
			    }else if (S.hostName.contains ("ebellepet")) {
			    	company.calendarStart(8)
			    	company.calendarEnd(19)
					company.appType(7)
					company.calendarInterval(30)
			      " e-bellepet "
			    }else{
					company.appType(1)
					company.calendarInterval(30)
			      " e-belle "
			    }
			company.name(name)
			company.phone(phone)
			company.contact(contact)
			company.email(email)
			company.obs(product1)
			company.validate match {
				case Nil =>{
						company.save
						UserCreateActors ! company
						return{
								<h2>
									Processo de cadastro iniciado<br/> 
									com sucesso para empresa<br/>
									{company.name}.
								</h2>

								<div>
									Aguarde o recebimento do e-mail de confirmação.
								</div>
								<div>
									Atenção: a mensagem pode cair na caixa de spam, <br/>
									junk, ou lixo eletrônico, verifique caso o e-mail <br/>
									não chegue em alguns minutos.
								</div>
						}
				}
				case xs => {
					S.error(xs)
				}
			}
		}
		in
	}
	def company = AuthUtil company
	def name = {
		<span>{company.name.is}</span>
	}
	def phone = {
		<span>{company.phone.is}</span>
	}	
	def thumb = {	
		company.imagethumb.is match {
			case img:String if(img != "") => {
				<img width="70px" src={company.thumb_web}/>
			}
			case _ => <img width="70px" style="padding-right: 10px" src="/images/logo.png"/>
		}
	}
	def logo = {	
		company.image.is match {
			case img:String if(img != "") => {
				<img width="110px" src={company.logo_web}/>
			}
			case _ => <img  src="/images/logo.png"/>
		}
	}	
	def form = {
			def process(): JsCmd= {
				try {
					company.save	
				   	S.notice("Alterado com sucesso!")
		   		}catch{
					case (e:net.liftweb.http.ResponseShortcutException) =>{
						throw e
					}
					case (e:Exception) => {
						S.error(e.getMessage)
					}
				}
			}
			//
//		    "name=status" #> (SHtml.select(status,Full(company.status.is.toString),(v:String) => company.status(v.toInt)))&
		    "name=status" #>(SHtml.text(company.status.is.toString,(s:String) => company.status(s.toInt)))&
		    "name=apptype" #>(SHtml.text(company.appType.is.toString,(s:String) => company.appType(s.toInt)))&
		    "name=bpIdForCompany" #>(SHtml.text(company.bpIdForCompany.is.toString,(s:String) => company.bpIdForCompany(s.toInt)))&
			"name=userActivityAssociate" #> (SHtml.checkbox(company.userActivityAssociate_?, company.userActivityAssociate_?(_)))&
			"name=showSalesToUser" #> (SHtml.checkbox(company.showSalesToUser_?, company.showSalesToUser_?(_)))&
			"name=useTreatmentAsAClass" #> (SHtml.checkbox(company.useTreatmentAsAClass_?, company.useTreatmentAsAClass_?(_)))&
			"name=autoIncrementCommand" #> (SHtml.checkbox(company.autoIncrementCommand_?, company.autoIncrementCommand_?(_)))&
			"name=autoOpenCalendar" #> (SHtml.checkbox(company.autoOpenCalendar_?, company.autoOpenCalendar_?(_)))&
			"name=allowrepeatcommand" #> (SHtml.checkbox(company.allowRepeatCommand_?, company.allowRepeatCommand_?(_)))&
			"name=notify" #> (SHtml.checkbox(company.senNotifications_?, company.senNotifications_?(_)))&
		    "name=financialNotification" #>(SHtml.text(company.financialNotification.is.toString,(s:String) => company.financialNotification(s.toInt)))&
		    "name=customerNotification" #>(SHtml.text(company.customerNotification.is.toString,(s:String) => company.customerNotification(s.toInt)))&
		    "name=userNotification" #>(SHtml.text(company.userNotification.is.toString,(s:String) => company.userNotification(s.toInt)))&
		    "name=partner" #>(SHtml.text(company.partner.is.toString,(s:String) => company.partner(s.toInt)))&
		    "name=name" #> (SHtml.text(company.name.is,company.name(_)))&
		    "name=obs" #> (SHtml.textarea(company.obs.is, company.obs(_)))&
		    "name=toCancelAnAppointment" #> (SHtml.textarea(company.toCancelAnAppointment.is, company.toCancelAnAppointment(_)))&
		    "name=bpmStartDay" #>(SHtml.text(company.bpmStartDay.is.toString,(s:String) => company.bpmStartDay(s.toInt)))&
		    "name=bpmDaysToAlert" #>(SHtml.text(company.bpmDaysToAlert.is.toString,(s:String) => company.bpmDaysToAlert(s.toInt)))&
		    "name=bpmDaysToEmail" #>(SHtml.text(company.bpmDaysToEmail.is.toString,(s:String) => company.bpmDaysToEmail(s.toInt)))&
			"name=bpmCommissionOnSale" #> (SHtml.checkbox(company.bpmCommissionOnSale_?, company.bpmCommissionOnSale_?(_)))&
			"name=bpmCommissionOnReady" #> (SHtml.checkbox(company.bpmCommissionOnReady_?, company.bpmCommissionOnReady_?(_)))&
			"name=bpmCommissionOnMissed" #> (SHtml.checkbox(company.bpmCommissionOnMissed_?, company.bpmCommissionOnMissed_?(_)))&
			"name=offCommissionOnReady" #> (SHtml.checkbox(company.offCommissionOnReady_?, company.offCommissionOnReady_?(_)))&
			"name=offCommissionOnMissed" #> (SHtml.checkbox(company.offCommissionOnMissed_?, company.offCommissionOnMissed_?(_)))&
			"name=packCommissionOnReady" #> (SHtml.checkbox(company.packCommissionOnReady_?, company.packCommissionOnReady_?(_)))&
			"name=packCommissionOnMissed" #> (SHtml.checkbox(company.packCommissionOnMissed_?, company.packCommissionOnMissed_?(_)))&
			"name=categoryOnProduct" #> (SHtml.checkbox(company.categoryOnProduct_?, company.categoryOnProduct_?(_)))&
		    "name=short_name" #> (SHtml.text(company.short_name.is,company.short_name(_)))&
		    "name=phone" #>(SHtml.text(company.phone.is,company.phone(_)))&
		    "name=calendarInterval" #>(SHtml.text(company.calendarInterval.is.toString,(s:String) => company.calendarInterval(s.toInt)))&
		    "name=calendarIntervalAlt" #>(SHtml.text(company.calendarIntervalAlt.is.toString,(s:String) => company.calendarIntervalAlt(s.toInt)))&
			"name=calendarStart" #>(SHtml.text(company.calendarStart.is.toString,(s:String) => company.calendarStart(s.toInt)))&		    
			"name=calendarEnd" #>(SHtml.text(company.calendarEnd.is.toString,(s:String) => company.calendarEnd(s.toInt)))&
			"name=calendarShowId" #> (SHtml.checkbox(company.calendarShowId_?, company.calendarShowId_?(_)))&
			"name=calendarShowPhone" #> (SHtml.checkbox(company.calendarShowPhone_?, company.calendarShowPhone_?(_)))&
			"name=calendarShowLight" #> (SHtml.checkbox(company.calendarShowLight_?, company.calendarShowLight_?(_)))&
			"name=calendarShowInterval" #> (SHtml.checkbox(company.calendarShowInterval_?, company.calendarShowInterval_?(_)))&
			"name=calendarShowActivity" #> (SHtml.checkbox(company.calendarShowActivity_?, company.calendarShowActivity_?(_)))&
			"name=calendarShowDifUnit" #> (SHtml.checkbox(company.calendarShowDifUnit_?, company.calendarShowDifUnit_?(_)))&
		    "name=contact" #>  (SHtml.text(company.contact.is,company.contact(_)))&
		    "name=website" #> (SHtml.text(company.website.is, company.website(_))) &
		    "name=email" #> (SHtml.text(company.email.is,company.email(_)))&
			"name=email_dis" #> (SHtml.text(company.email.is,s => s,"disabled" -> "true")++SHtml.hidden(process))
		
	}

}

