package code
package model 

import net.liftweb.common.{Box,Full,Empty}
import net.liftweb._ 
import mapper._
import http._ 
import SHtml._ 
import util._ 
import code.util._
import _root_.java.math.MathContext; 
import java.util.Date
import http.js._

class TdEpet extends Audited[TdEpet] with IdPK with CreatedUpdated with CreatedUpdatedBy with PerCompany with net.liftweb.common.Logger{
    def getSingleton = TdEpet
    object treatmentDetail extends MappedLongForeignKey(this,TreatmentDetail)
    object animal extends MappedLongForeignKey(this,AnimalPartner)
    
}

object TdEpet extends TdEpet with LongKeyedMapperPerCompany[TdEpet] with OnlyCurrentCompany[TdEpet]{
    
}
