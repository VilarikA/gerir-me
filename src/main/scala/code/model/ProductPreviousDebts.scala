package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 
import _root_.java.util.Date;
import net.liftweb.common.{Box,Full,Empty}
import code.util._
class ProductPreviousDebts extends ProductMapper[ProductPreviousDebts]{
    def getSingleton = ProductPreviousDebts
    object productClass extends MappedEnum(this,ProductType.Types){
        override def defaultValue = ProductType.Types.PreviousDebts;
    }
}

object ProductPreviousDebts extends ProductPreviousDebts with  ProductMapperMeta[ProductPreviousDebts]{
    def productPreviousDebts:ProductPreviousDebts = ProductPreviousDebts.findAllInCompany(By(ProductPreviousDebts.productClass,ProductType.Types.PreviousDebts))(0)
    def productCredits:ProductPreviousDebts = ProductPreviousDebts.findAllInCompany(By(ProductPreviousDebts.productClass,ProductType.Types.CustomerCredits))(0)
}