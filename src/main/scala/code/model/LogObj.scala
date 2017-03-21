package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._
import _root_.java.math.MathContext
import net.liftweb.mapper.DB

class LogObj extends LongKeyedMapper[LogObj] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = LogObj
    object message extends MappedPoliteString(this,100000)
    object typeLog extends MappedPoliteString(this,255)
}

object LogObj extends LogObj with LongKeyedMapperPerCompany[LogObj]{
    override def dbTableName = "log.logobj"

    def wLogObj (company:Long, message:String, typeLog:String) {
    	val log = LogObj.create 
    	log.company(company)
    	.message(message)
    	.typeLog(typeLog)
    	.save
    }

}

case class LogMessage(message:String,typeLog:String);