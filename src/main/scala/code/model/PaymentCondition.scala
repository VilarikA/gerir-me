package code
package model 

import net.liftweb._ 
import scala.xml._
import mapper._ 
import http._ 
import SHtml._ 
import util._
import code.util._
import _root_.java.math.MathContext
import net.liftweb.mapper.DB
import net.liftweb.common._
import java.util.Date


class PaymentCondition extends LongKeyedMapper[PaymentCondition] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy with ActiveInactivable[PaymentCondition] {
    def getSingleton = PaymentCondition
    object days extends MappedInt(this)
    object paymentDate extends MappedDate (this)
    object percent extends MappedDouble (this)
    object value extends MappedCurrency(this)
    object obs extends MappedPoliteString(this,555)
    object project extends MappedLongForeignKey(this,Project1)

    def addPaymentCondition(projectId:Long, days:Int, paymentdate:Date, percent:Float, value:Float, obs:String){
    	PaymentCondition
                .createInCompany
                .project(projectId)
                .days(days)
                .percent(percent)
                .value(value)
                .paymentDate(paymentdate)
                .obs(obs)
                .save

	}    
}

object PaymentCondition extends PaymentCondition with LongKeyedMapperPerCompany[PaymentCondition]  
with  OnlyCurrentCompany[PaymentCondition] with  OnlyActive[PaymentCondition] {
}
