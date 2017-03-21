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

object ProductLineTag extends ProductLineTag with LongKeyedMapperPerCompany[ProductLineTag]  with  OnlyCurrentCompany[ProductLineTag]{
    def join(line:ProductLine, product:Product){
         ProductLineTag.createInCompany.product(product).line(line).save
    }
    def join(line:Long, product:Long){
         ProductLineTag.createInCompany.product(product).line(line).save
    } 
}

//class ProductLineTag extends LongKeyedMapper[ProductLineTag] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy{ 
class ProductLineTag extends Audited[ProductLineTag] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy{ 
    def getSingleton = ProductLineTag
    object product extends MappedLongForeignKey(this,Product)
    object line extends MappedLongForeignKey(this,ProductLine)
}