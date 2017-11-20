
package code
package model 

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

class BusinessPatternPayroll extends LongKeyedMapper[BusinessPatternPayroll] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = BusinessPatternPayroll
    object business_pattern extends MappedLong(this){
        lazy val obj = Customer.findByKey(this.is)
    }
    object event extends MappedLong(this){
        lazy val obj = PayrollEvent.findByKey(this.is)
    }
    object qtd extends MappedLong(this)
    object value extends MappedCurrency(this)
    object date extends EbMappedDate(this)
    object obs extends MappedString(this, 255)
}
object BusinessPatternPayroll extends BusinessPatternPayroll with LongKeyedMapperPerCompany[BusinessPatternPayroll] with OnlyCurrentCompany[BusinessPatternPayroll]{

}