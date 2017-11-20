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

//class BusinessPatternConsideration extends LongKeyedMapper[BusinessPatternConsideration] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy {
class BusinessPatternConsideration extends Audited[BusinessPatternConsideration] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = BusinessPatternConsideration
    object business_pattern extends MappedLong(this)
    object message extends MappedPoliteString(this,255)
    object notify_type extends MappedInt(this)
    object consideration_type extends MappedLong(this)
    object date extends EbMappedDate(this)
}

object BusinessPatternConsideration extends BusinessPatternConsideration with LongKeyedMapperPerCompany[BusinessPatternConsideration] with OnlyCurrentCompany[BusinessPatternConsideration]{
    val ALWAYS_NOTIFY = 1
    val ONCE_NOTFY = 2
    val NOT_NOTING = 3 
    def noficationsNow(business_pattern:Long) = {
        val result = BusinessPatternConsideration.findAll(By(BusinessPatternConsideration.business_pattern,business_pattern),ByList(notify_type,List(ALWAYS_NOTIFY,ONCE_NOTFY)))
        result.filter(_.notify_type.is==ONCE_NOTFY).foreach(_.notify_type(NOT_NOTING).save)
        result.map(_.message.is)
    }
    override def dbTableName = "bpconsideration"
}