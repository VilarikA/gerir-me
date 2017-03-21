package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class InstructionDegree extends LongKeyedMapper[InstructionDegree] with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = InstructionDegree 
    object name extends MappedPoliteString(this,255)
} 

object InstructionDegree extends InstructionDegree with LongKeyedMetaMapper[InstructionDegree]{

}
