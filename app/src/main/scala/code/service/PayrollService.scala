package code
package service

import net.liftweb._
import mapper._ 
import code.util._
import code.comet._
import model._
import net.liftweb.util._
import http.js._
import JE._
import net.liftweb.common._
//import InventoryMovement._
import java.util.Date
import java.util.Calendar

object PayrollService extends  net.liftweb.common.Logger {
	def commisionEvents = PayrollEvent.findAllInCompany(By(PayrollEvent.isCommition_?, true))
	def advanceEvents = PayrollEvent.findAllInCompany(By(PayrollEvent.isAdvance_?, true))
	def factoryBPPayroll(obs:String, date:Date, event:PayrollEvent, value:Double, business_pattern:User, qtd:Int=1, id:Long=0) ={
		val bppr = if(id != 0){
						BusinessPatternPayroll.findByKey(id).get
					}else{
						BusinessPatternPayroll.createInCompany
					}
		
	  bppr.obs(obs)
	  .date(date)
	  .business_pattern(business_pattern.id.is)
	  .event(event.id.is)
	  .value(value)
	  .qtd(qtd)
	}
	
	def clearCommisionEvents(start:Date, end:Date){
		if(!commisionEvents.isEmpty)
			BusinessPatternPayroll.findAllInCompany(By(BusinessPatternPayroll.event, commisionEvents(0).id.is), BySql("date(date_c) between date(?) and date(?)",IHaveValidatedThisSQL("",""),start, end)).foreach(_.delete_!)
	}
	def clearAdvanceEvents(start:Date, end:Date){
		if(!advanceEvents.isEmpty)
			BusinessPatternPayroll.findAllInCompany(By(BusinessPatternPayroll.event, advanceEvents(0).id.is), BySql("date(date_c) between date(?) and date(?)",IHaveValidatedThisSQL("",""),start, end)).foreach(_.delete_!)
	}	
	def processEvents(start:Date, end:Date, dttypes:String) = {
		clearCommisionEvents(start, end)
		clearAdvanceEvents(start, end)
		val professionals = User.findAllInCompanyOrdened();
		professionals.foreach((p)=>{
			processCommisionEvents(p, start, end)
			processAdvacendEvents(p, start, end, dttypes)
		})
	}
	def dateTranslate(date:Date) = Project.dateToStr(date)

	def processCommisionEvents(professional:User, start:Date, end:Date) {
		if(!commisionEvents.isEmpty){
			val totalCommision = Commision.totalCommsion(professional, start, end)
			val bpPayroll = factoryBPPayroll("Comissões de %s até %s".format(dateTranslate(start), dateTranslate(end)), end, commisionEvents(0), totalCommision,professional)
			bpPayroll.save
		}
	}

	def processAdvacendEvents(professional:User, start:Date, end:Date, dttypes:String) {
		if(!advanceEvents.isEmpty){
			//
			// REVER passagem deparametro unidade fixo 1 = 1
			//
			val total = AccountPayable.findAllByStartEndOnlyPaidByUser(start, end, 
				AuthUtil.company, " 1 = 1 ", professional.id.is.toString, dttypes).foldLeft(0.00)(_+_.realValue)
			val bpPayroll = factoryBPPayroll("Adiantamentos de %s até %s".format(dateTranslate(start), dateTranslate(end)), end, advanceEvents(0), total, professional)
			bpPayroll.save
		}
	}	
}