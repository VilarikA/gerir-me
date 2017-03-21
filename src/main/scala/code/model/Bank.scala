package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class Bank extends LongKeyedMapper[Bank] with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = Bank 
    object banknumber extends MappedPoliteString(this,15)
    object short_name extends MappedPoliteString(this,15)
    object name extends MappedPoliteString(this,255)
	object website extends MappedPoliteString(this,255)
	object logo extends MappedPoliteString(this,255)
} 

object Bank extends Bank with LongKeyedMetaMapper[Bank]{

}
