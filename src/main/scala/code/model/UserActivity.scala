package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import _root_.java.math.MathContext; 
import net.liftweb.common.{Box,Full,Empty}
import net.liftweb.http.js._

class UserActivity extends Audited[UserActivity] with IdPK with CreatedUpdated with CreatedUpdatedBy with PerCompany with Restable[UserActivity]  { 

    def getSingleton = UserActivity
    object duration extends MappedPoliteString(this,10)
    object price extends MappedCurrency(this)
    object obs extends MappedPoliteString(this,255)
    object commission extends MappedDecimal(this,MathContext.DECIMAL64,4) {
        override def defaultValue = 0.00
    }
    object commissionAbs extends MappedCurrency(this) {
        override def defaultValue = 0.00
    }
    object auxPrice extends MappedCurrency(this) {// valor pago ao assistente
        override def defaultValue = 0.00
    }
    object auxHousePrice extends MappedCurrency(this) {// valor pago ao assistente pela casa
        override def defaultValue = 0.00
    }
    object auxPercent extends MappedDecimal(this,MathContext.DECIMAL64,4) {// percentual pago ao assistente
        override def defaultValue = 0.00
    }
    object auxHousePercent extends MappedDecimal(this,MathContext.DECIMAL64,4) {// percentual pago ao assistente pela casa
        override def defaultValue = 0.00
    }
    object user extends MappedLongForeignKey(this,User)
    object producttype extends MappedLongForeignKey(this,ProductType)

    def use_product_price_? = use_product_price.is
    def use_product_commission_? = use_product_commission.is

    object use_product_price extends MappedBoolean(this){
        override def defaultValue = true
        override def dbColumnName = "use_product_price"
    }

    object use_product_commission extends MappedBoolean(this){
        override def defaultValue = true
        override def dbColumnName = "use_product_commission"
    }    

    object activity extends MappedLongForeignKey(this,Activity){
        override def dbIndexed_? = true
    }

    def typeName = producttype.obj match {
            case Full(a:ProductType) => a.name.is
            case _ => ""
    } 

    def name:String = {
    	activity.obj match {
    		case Full(a:Activity) => a.name.is
    		case _ => ""
    	}
    	
    }
    override def suplementalJs(ob: Box[KeyObfuscator]): List[(String, JsExp)] = {
        activity.obj.get.name.asJs 
    }    
}

object UserActivity extends UserActivity with LongKeyedMapperPerCompany[UserActivity] with OnlyCurrentCompany[UserActivity] with MetaRestable[UserActivity]

