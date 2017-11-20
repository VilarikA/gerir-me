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

class InventoryCause extends LongKeyedMapper[InventoryCause] //with PerCompany 
	with IdPK with CreatedUpdated with CreatedUpdatedBy{
    def getSingleton = InventoryCause
    object name extends MappedPoliteString(this,255)
    object obs extends MappedPoliteString(this,255)
    lazy val forSale_? = this.id.is == AuthUtil.company.inventoryCauseSale.is
    lazy val forPurchase_? = this.id.is == AuthUtil.company.inventoryCausePurchase.is
    lazy val forTasfer_? = this.id.is == AuthUtil.company.inventoryCauseTrasfer.is
}
object InventoryCause extends InventoryCause 
	with LongKeyedMetaMapper[InventoryCause]  

