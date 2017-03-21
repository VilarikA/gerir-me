
package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class Council extends LongKeyedMapper[Council] with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = Council 
    object name extends MappedPoliteString(this,255)
    object status extends MappedInt(this)
} 

object Council extends Council with LongKeyedMetaMapper[Council]{

}
