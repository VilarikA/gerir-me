package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class Occupation extends LongKeyedMapper[Occupation] with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = Occupation 
    object name extends MappedPoliteString(this,255)
} 

object Occupation extends Occupation with LongKeyedMetaMapper[Occupation]{

}
