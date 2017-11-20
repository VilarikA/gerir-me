package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 

class Breed extends LongKeyedMapper[Breed] with IdPK {
    def getSingleton = Breed
    object name extends MappedPoliteString(this,255)
    object short_name extends MappedPoliteString(this,20)
    object obs extends MappedPoliteString(this,555)
    object species extends MappedLong(this)
} 

object Breed extends Breed with LongKeyedMetaMapper[Breed]{
}

class Species extends LongKeyedMapper[Species] with IdPK {
    def getSingleton = Species
    object name extends MappedPoliteString(this,255)
    object short_name extends MappedPoliteString(this,20)
} 

object Species extends Species with LongKeyedMetaMapper[Species]{
}
