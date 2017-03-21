
package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class HonorificTitle extends LongKeyedMapper[HonorificTitle] with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = HonorificTitle 
    object name extends MappedPoliteString(this,255)
    object status extends MappedInt(this)
} 

object HonorificTitle extends HonorificTitle with LongKeyedMetaMapper[HonorificTitle]{

}
