package code
package model 

import code.actors._
import code.service._
import net.liftweb._ 
import mapper._ 
import net.liftweb.common._
import scalendar._
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext;
import net.liftweb.common.{Box,Full}
import code.util._
import _root_.java.util.Calendar
import _root_.java.util.Date


class BpMonthly extends Audited[BpMonthly] with PerCompany with IdPK 
	with CreatedUpdated with CreatedUpdatedBy with ActiveInactivable[BpMonthly] with net.liftweb.common.Logger {
    def getSingleton = BpMonthly
    object business_pattern extends MappedLongForeignKey(this, Customer) with LifecycleCallbacks { // em função do número de dias
      override def beforeSave() {
          super.beforeSave;
          if (business_pattern.isEmpty) {
             throw new RuntimeException("Informe um " + AuthUtil.company.appCustName("cliente") + "!");
          }
      } 
    }

    object product extends MappedLongForeignKey(this,Activity)
    object startAt extends EbMappedDate(this) 
    object endAt extends EbMappedDate(this) with LifecycleCallbacks {
        override def beforeSave() {
            super.beforeSave;
            if(this.get < startAt.is){
                //this.set(dueDate.is)
                throw new RuntimeException("Data de fim deve ser maior que data de início!")
            }
        }       
    }
    object weekDays extends MappedPoliteString(this,255)
    object value extends MappedCurrency(this)
    object valueDiscount extends MappedCurrency(this) // em função de forma de pagamento
    object valueSession extends MappedCurrency(this)  with LifecycleCallbacks { // em função do número de dias
      override def beforeSave() {
          super.beforeSave;
          if (fixValueSession_?) {
            // nada 
          } else {
            this.set(sessionValue)
          }
      } 
    }
    object fixNumSession_? extends MappedBoolean(this) {
        override def defaultValue = false
        override def dbColumnName = "fixNumSession"
    }
    object fixValueSession_? extends MappedBoolean(this) {
        override def defaultValue = false
        override def dbColumnName = "fixValueSession"
    }
    object numSession extends MappedInt (this)  with LifecycleCallbacks { // em função do número de dias
      override def beforeSave() {
          super.beforeSave;
          if (fixNumSession_?) {
            // nada 
          } else {
            this.set(num)
          }  
      } 
    }
    object user extends MappedLong(this) // para associar um profissional e listar as sessões com prof diferente (reposições)
    object obs extends MappedPoliteString(this,255)
    object canceled_? extends MappedBoolean(this) {
        override def defaultValue = false
        override def dbColumnName = "canceled"
    }
    object unit extends MappedLongForeignKey(this,CompanyUnit) 
    object bpmCount extends MappedInt (this) with LifecycleCallbacks {
      override def defaultValue = 1
      override def beforeSave() {
          super.beforeSave;
          if (this.get == 0) {
            this.set(1)
          }  
      } 
    }

    lazy val weekDaysInt = {
        weekDays split(",") filter( _ != "") map( _.toInt ) toList
    }

    def end_tomorrow = {
      val cal = Calendar.getInstance()
      cal.setTime(endAt); 
      cal.add(java.util.Calendar.DATE, 1);
      cal.getTime()
    }

    def duration = {
        Scalendar(startAt.getTime()) to Scalendar(end_tomorrow.getTime()) by 1.day
    }

    def weekDay(date:Date) = {
        val c = Calendar.getInstance();
        c.setTime(date);        
        c.get(Calendar.DAY_OF_WEEK) - 1
    }

    def num = if (this.fixNumSession_?) {
            this.numSession.is
        } else {
            duration.filter( (duration:Duration)=> {
            weekDaysInt.exists( _ == weekDay(duration.start))
            }).size
        }

    def sessionValue = {
        if (num > 0) {
            this.valueDiscount.is / num;
        } else {
            0.0;
        }
    }
    def productName = product.obj match {
        case Full(t) => t.name.is
        case _ => ""
    }
    
    def bpName = business_pattern.obj match {
        case Full(t) => t.name.is
        case _ => ""
    }

    val today = Project.date_format_db.parse(Project.date_format_db.format(new Date()));
    def msgAlerta = if (this.endAt.is > today) {
        productName + " vai expirar em " + Project.dateToStr(this.endAt.is)
    } else if (this.endAt.is == today) {
        productName + " está expirando hoje!"
    } else {
        productName + " já expirou em " + Project.dateToStr(this.endAt.is)
    }

    def unitsToShowSql = if (AuthUtil.user.isAdmin) {
      " 1 = 1 "
    } else {
      " (bm.unit = %s or (bm.unit in (select uu.unit from usercompanyunit uu where uu.user_c = %s and uu.company = %s))) ".format(AuthUtil.user.unit, AuthUtil.user.id, AuthUtil.user.company)
    }

}

object BpMonthly extends BpMonthly with LongKeyedMapperPerCompany[BpMonthly] 
	with OnlyCurrentCompany[BpMonthly]  with OnlyActive[BpMonthly] {
    
    def findByCustomer(customer:Customer, date:Date) = {
        BpMonthly.findAllInCompany(
            By(BpMonthly.business_pattern, customer), 
            BySql("(? between startat and endat)", IHaveValidatedThisSQL("",""), date),
            OrderBy(BpMonthly.createdAt, Ascending) // pra o antigo vir primeiro
            )
    }

    def monthlyByProduct[T <: code.model.ProductMapper[T]](product:ProductMapper[T], customer:Customer, date:Date) = {
        BpMonthly.findAll(
                            By(BpMonthly.business_pattern, customer.id.is), 
                            By(BpMonthly.product, product.id.is),
                            BySql("(? between startat and endat)", IHaveValidatedThisSQL("",""), date)
                        )(0)
    }
    // só fiz o cout pq não sei testar se o retorno é válido - Rigel
    def countBpMonthlyByProduct[T  <: code.model.ProductMapper[T]](product:ProductMapper[T], customer:Customer, date:Date) = {
        BpMonthly.count(
                            By(BpMonthly.business_pattern, customer.id.is), 
                            By(BpMonthly.product, product.id.is),
                            BySql("(? between startat and endat)", IHaveValidatedThisSQL("",""), date)
                        )
    }
    def registerActivityBpMonthly(customer:Customer, user:User, unit:CompanyUnit, product:Product, value:Double, valueDiscount:Double, start:Date, end:Date){
        def start_ant = {
          val cal = Calendar.getInstance()
          cal.setTime(start); 
          cal.add(java.util.Calendar.MONTH, -1);
          cal.getTime()
        }
        /* busca mensalidade do mes anterior */
        val bpcount = BpMonthly.countBpMonthlyByProduct (product, customer, start_ant);
        if (bpcount > 0) {
            val bpmonthly_ant = BpMonthly.monthlyByProduct (product, customer, start_ant)
            val weekDays = bpmonthly_ant.weekDays
            val obs = bpmonthly_ant.obs
            BpMonthly
                .create
                .company(customer.company)
                .business_pattern(customer.id.is)
                .product(product.id.is)
                .startAt(start)
                .endAt(end)
                .weekDays(weekDays)
                .value(value)
                .valueDiscount(valueDiscount)
                .unit(unit.id.is)
                .user(user.id.is)
                .obs(obs)
                .bpmCount(product.bpmCount)
                .save
        } else {
            BpMonthly
                .create
                .company(customer.company)
                .business_pattern(customer.id.is)
                .product(product.id.is)
                .startAt(start)
                .endAt(end)
                .weekDays("")
                .value(value)
                .valueDiscount(valueDiscount)
                .unit(unit.id.is)
                .user(user.id.is)
                .obs("")
                .bpmCount(product.bpmCount)
                .save
        }
    }     
    def noficationsNow(business_pattern:Long) = {
/*        val result = BpMonthly.findAll(By(BpMonthly.business_pattern,business_pattern),
                NotBy(BpMonthly.canceled_?,true))
        //result.filter(_.notify_type.is==ONCE_NOTFY).foreach(_.notify_type(NOT_NOTING).save)
        result.map(_.obs.is)
        //result.map(_.productName + ' ' + obs.is)
*/
        val result = BpMonthly.findAll(OrderBy(BpMonthly.endAt, Ascending),BySql(
            """company = ? and business_pattern = ? and date (now()) + ? > endat 
            and canceled = false
            and product not in (select product from bpmonthly bm1 where bm1.business_pattern = bpmonthly.business_pattern 
            and bm1.company = bpmonthly.company    
            and bm1.product = bpmonthly.product and (bm1.startat > date (now()) or bm1.startat > bpmonthly.endat))
            and (select typeproduct from product where id = product) not in (select pr.typeproduct from bpmonthly bm1 
            inner join product pr on pr.id = bm1.product
            where bm1.business_pattern = bpmonthly.business_pattern 
            and bm1.company = bpmonthly.company    
            and (bm1.startat > date (now()) or bm1.startat > bpmonthly.endat))            
            """,
            IHaveValidatedThisSQL("",""), AuthUtil.company.id.is, business_pattern, AuthUtil.company.bpmDaysToAlert.is))
        //result.filter(_.notify_type.is==ONCE_NOTFY).foreach(_.notify_type(NOT_NOTING).save)
 //       result.map(_.obs.is + " complemento " + _.obs.is)  
        result.map(_.msgAlerta)
    }

}