package code
package model 

import code.actors._
import code.service._
import net.liftweb._ 
import mapper._ 
import net.liftweb.common._
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 
import net.liftweb.widgets.gravatar.Gravatar
import code.util._
import _root_.java.util.Calendar
import _root_.java.util.Date

class CustomerAccountHistory extends LongKeyedMapper[CustomerAccountHistory] with IdPK with CreatedUpdated with CreatedUpdatedBy  with PerCompany with WithCustomer{
    def getSingleton = CustomerAccountHistory 
    object description extends MappedPoliteString(this,455)
    object treatment extends MappedLongForeignKey(this,Treatment){
        override def dbIndexed_? = true
    }
    object treatmentDetail extends MappedLongForeignKey(this,TreatmentDetail){
        override def dbIndexed_? = true
    }    
    object payment extends MappedLongForeignKey(this,Payment){
        override def dbIndexed_? = true
    }
    object paymentDetail extends MappedLongForeignKey(this,PaymentDetail){
        override def dbIndexed_? = true
    }
    object currentValue extends MappedDouble(this)
    object value extends MappedDouble(this)
}
object CustomerAccountHistory  extends CustomerAccountHistory with LongKeyedMapperPerCompany[CustomerAccountHistory]