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

class TdEdoctus extends Audited[TdEdoctus] with IdPK with CreatedUpdated with CreatedUpdatedBy with PerCompany with net.liftweb.common.Logger{
    def getSingleton = TdEdoctus
    object treatmentDetail extends MappedLongForeignKey(this,TreatmentDetail)
    object wayOfAccess extends MappedString(this, 1)
    object tooth extends MappedString(this, 20)
    
    def toXmlTiss = {
       var strXml:String ="""
       """
        strXml
    }

}

object TdEdoctus extends TdEdoctus with LongKeyedMapperPerCompany[TdEdoctus] with OnlyCurrentCompany[TdEdoctus]{
    
}
