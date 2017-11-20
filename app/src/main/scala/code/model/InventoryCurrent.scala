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

class InventoryCurrent extends LongKeyedMapper[InventoryCurrent] with PerCompany with PerUnit with IdPK with CreatedUpdated with CreatedUpdatedBy {
    def getSingleton = InventoryCurrent
    object currentStock extends MappedDecimal(this,MathContext.DECIMAL64,2)
    object product extends MappedLongForeignKey(this,Product)
    object averagePrice extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0.00
    }
    object averageIndic1 extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0.00
    }
    object averageIndic2 extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0.00
    }
    object averageIndic3 extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0.00
    }
    object averageIndic4 extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0.00
    }
    object averageIndic5 extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0.00
    }
}

object InventoryCurrent extends InventoryCurrent with LongKeyedMapperPerCompany[InventoryCurrent]  with  OnlyCurrentCompany[InventoryCurrent] {

    def currentStock(qtd:Float, product:Product, unit:CompanyUnit) = {
        if(unit == null){
            throw new RuntimeException("Unidade não pode ser nula!");
        }
        InventoryCurrent.findAll(
                                By(InventoryCurrent.product,product),
                                By(InventoryCurrent.unit,unit)
                                ) match {
                                    case i::Nil => i match {
                                        case  ip:InventoryCurrent => ip.currentStock(BigDecimal(qtd)).save
                                    }
                                    case _ => InventoryCurrent.create.product(product).unit(unit).currentStock(BigDecimal(qtd)).save
                                }    
    }

    def currentStock(product:Product, unit:CompanyUnit):Float =
        InventoryCurrent.findAll(
                                By(InventoryCurrent.product,product),
                                By(InventoryCurrent.unit,unit)
                                ) match {
                                    case i::Nil => i match {
                                        case  ip:InventoryCurrent => ip.currentStock.is.toFloat
                                    }
                                    case _ => 0
                                }
                                
    def currentStockAverage(averagePrice:BigDecimal, averageIndic1:BigDecimal,averageIndic2:BigDecimal,
        averageIndic3:BigDecimal,averageIndic4:BigDecimal,averageIndic5:BigDecimal,
        product:Product, unit:CompanyUnit) = {
        if(unit == null){
            throw new RuntimeException("Unidade não pode ser nula!");
        }
        InventoryCurrent.findAll(
                                By(InventoryCurrent.product,product),
                                By(InventoryCurrent.unit,unit)
                                ) match {
                                    case i::Nil => i match {
                                        case  ip:InventoryCurrent => 
                                            ip.averagePrice((averagePrice)).
                                                averageIndic1((averageIndic1)).
                                                averageIndic2((averageIndic2)).
                                                averageIndic3((averageIndic3)).
                                                averageIndic4((averageIndic4)).
                                                averageIndic5((averageIndic5)).
                                                save
                                    }
                                    case _ => InventoryCurrent.create.product(product).unit(unit).
                                    averagePrice((averagePrice)).
                                    averageIndic1((averageIndic1)).
                                    averageIndic2((averageIndic2)).
                                    averageIndic3((averageIndic3)).
                                    averageIndic4((averageIndic4)).
                                    averageIndic5((averageIndic5)).
                                    save
                                }    
    }
}