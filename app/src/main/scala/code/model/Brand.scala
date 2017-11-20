package code
package model 

import net.liftweb._ 
import mapper._ 
import net.liftweb.common._
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 
import net.liftweb.widgets.gravatar.Gravatar
import code.util._
import _root_.java.util.Calendar
import _root_.java.util.Date

class Brand extends BusinessPattern[Brand]{
    def getSingleton = Brand

}

object Brand extends Brand with BusinessPatternMeta[Brand]{
  override def findAll(params: QueryParam[Brand]*): List[Brand] = {
        super.findAll(By(is_brand_?,true) :: params.toList :_*)
    }

    override def count(params: QueryParam[Brand]*): Long = {
        super.count(By(is_brand_?,true) :: params.toList :_*)
    }
    

    override def findAll(): List[Brand] = {
        super.findAll(By(is_brand_?,true))
    }
}