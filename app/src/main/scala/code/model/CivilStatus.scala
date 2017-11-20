package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class CivilStatus extends LongKeyedMapper[CivilStatus] with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = CivilStatus 
    object name extends MappedPoliteString(this,255)
} 

object CivilStatus extends CivilStatus with LongKeyedMetaMapper[CivilStatus]{

}
