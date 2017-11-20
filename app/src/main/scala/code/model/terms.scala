package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import net.liftweb.http.js.JE._
import SHtml._ 
import util._
import _root_.java.math.MathContext
import net.liftweb.mapper.DB


class Terms extends Audited[Terms] with PerCompany with PerUnit with IdPK with CreatedUpdated with CreatedUpdatedBy with NameSearchble[Terms] with ActiveInactivable[Terms] {
    def getSingleton = Terms

    override def updateShortName = false

    object message extends MappedPoliteString(this,40000)
    object obs extends MappedPoliteString(this,255)
    override def asJsToSelect = {
      JsObj(
            ("name",this.name.is),
            ("message",this.message.is),
            ("id",this.id.is)
        )
    }    
}
object Terms extends Terms with LongKeyedMapperPerCompany[Terms] with OnlyActive[Terms]
