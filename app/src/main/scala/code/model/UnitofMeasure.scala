
package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class UnitofMeasure extends LongKeyedMapper[UnitofMeasure] with PerCompany  with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = UnitofMeasure 
    object name extends MappedPoliteString(this,255)
} 

object UnitofMeasure extends UnitofMeasure with LongKeyedMetaMapper[UnitofMeasure]{

}
