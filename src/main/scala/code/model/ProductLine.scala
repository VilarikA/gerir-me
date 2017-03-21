package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import code.util._
import _root_.java.math.MathContext; 
import net.liftweb.common.{Box,Full}

class ProductLine extends Audited[ProductLine] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy with NameSearchble[ProductLine] with ActiveInactivable[ProductLine]{ 
    def getSingleton = ProductLine
    override def updateShortName = false
    object obs extends MappedPoliteString(this,255)
    object external_id extends MappedPoliteString(this,200)
    def productIds = ProductLineTag.findAll(By(ProductLineTag.line,this.id.is)).map(_.product.is)
}
object ProductLine extends ProductLine with LongKeyedMapperPerCompany[ProductLine]  with  OnlyActive[ProductLine]
