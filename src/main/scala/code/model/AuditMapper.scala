package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class AuditMapper extends LongKeyedMapper[AuditMapper] with IdPK with CreatedUpdated with CreatedUpdatedBy with PerCompany with MultiEntityRelated {
    def getSingleton = AuditMapper
    object jsObj extends MappedPoliteString(this,4000)
    object event extends MappedPoliteString(this,200)
} 

object AuditMapper extends AuditMapper with LongKeyedMapperPerCompany[AuditMapper]{
	override def dbTableName = "log.auditmapper"
}
