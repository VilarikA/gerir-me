package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import code.util._
import code.service._
import java.util.Calendar
import net.liftweb.common.{Box,Full}

class BusinessPatternAccount extends Audited[BusinessPatternAccount] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = BusinessPatternAccount
    object business_pattern extends MappedLong(this)
    object bank extends MappedLong(this)
    object account extends MappedPoliteString(this,255)
    object agency extends MappedPoliteString(this,255)
    object obs extends MappedPoliteString(this,255)
}

object BusinessPatternAccount extends BusinessPatternAccount with LongKeyedMapperPerCompany[BusinessPatternAccount] with OnlyCurrentCompany[BusinessPatternAccount]{
    override def dbTableName = "bpaccount"
}