package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class Operator extends LongKeyedMapper[Operator] with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = Operator 
    object name extends MappedPoliteString(this,255)
} 

object Operator extends Operator with LongKeyedMetaMapper[Operator]{

}
