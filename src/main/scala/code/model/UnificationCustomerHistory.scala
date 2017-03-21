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
//Class de cliente

class UnificationCustomerHistory extends LongKeyedMapper[UnificationCustomerHistory] with IdPK with CreatedUpdated with CreatedUpdatedBy  with PerCompany  {
    def getSingleton = UnificationCustomerHistory 
    object customerSource extends MappedLongForeignKey(this,Customer){
        override def dbIndexed_? = true
    }
    object customerDestination extends MappedLongForeignKey(this,Customer){
        override def dbIndexed_? = true
    }    
}
object UnificationCustomerHistory  extends UnificationCustomerHistory with LongKeyedMapperPerCompany[UnificationCustomerHistory] with OnlyCurrentCompany[UnificationCustomerHistory]
