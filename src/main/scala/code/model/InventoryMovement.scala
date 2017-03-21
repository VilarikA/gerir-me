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

class InventoryMovement extends LongKeyedMapper[InventoryMovement] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy with PerUnit with  net.liftweb.common.Logger { 
    def getSingleton = InventoryMovement
    object typeMovement extends MappedEnum(this,InventoryMovement.InventoryMovementType)
    object beforeStock extends MappedDecimal(this,MathContext.DECIMAL64,2)
    object obs extends MappedPoliteString(this,255) with LifecycleCallbacks {
      override def beforeSave() {
          super.beforeSave;
          if(this.get.length < 1){
            this.set(cause_name)
          }
      } 
    }
    object invoice extends MappedPoliteString(this,255)
    object amount extends MappedDecimal(this,MathContext.DECIMAL64,2)
    object efetiveDate extends EbMappedDate(this)
    object product extends MappedLongForeignKey(this,Product)
    object inventoryCause extends MappedLongForeignKey(this,InventoryCause)
    object business_pattern extends MappedLongForeignKey(this,Customer) // mateus ver se nao é caro
    object purchasePrice extends MappedDecimal(this,MathContext.DECIMAL64,2)
    object indic1 extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0;
    }
    object indic2 extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0;
    }
    object indic3 extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0;
    }
    object indic4 extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0;
    }
    object indic5 extends MappedDecimal(this,MathContext.DECIMAL64,2) {
        override def defaultValue = 0;
    }
    
    object salePrice extends MappedDecimal(this,MathContext.DECIMAL64,2)
    object totalSalePrice extends MappedDecimal(this,MathContext.DECIMAL64,2)
    object treatment_detail extends MappedLongForeignKey(this,TreatmentDetail){
        override def dbIndexed_? = true
    }
/*
    object treatment extends MappedLongForeignKey(this,Treatment){
        override def dbIndexed_? = true
    }
*/

    def unit_name:String = {
        unit.obj match {
            case Full(u) => u.name.is 
            case _ =>"" 
        }

    }
    def cause_name:String = {
        inventoryCause.obj match {
            case Full(u) => u.name.is 
            case _ =>"" 
        }

    }
    def cause(cause:InventoryCause) = {
        this.inventoryCause(cause)
        this
    }
    def day(date:Date) = {
        this.efetiveDate(date)
        this
    }
    def item(product:Product) = {
        this.product(product)
        this
    }

    def toInvoice(invoice:String) = {
        this.invoice(invoice)
        this   
    }
    def from(location:CompanyUnit){
        this.unit(location)
        save(location)
    }
    
    def +=(a:Float,b:Float) = {
        a+b
    }

    def -=(a:Float,b:Float) = {
        a-b
    }

    def causeObj:InventoryCause = {
        this.inventoryCause.obj match {
            case Full(inventoryCause) => { 
                inventoryCause
            }
            case _ => throw new InvalidProductInventoryException
        }
    }

    def productObj:Product = {
        this.product.obj match {
            case Full(product) => { 
                product
            }
            case _ => throw new InvalidProductInventoryException
        }
    }

    def unitObj:CompanyUnit = {
        this.unit.obj match {
            case Full(unit) => { 
                unit
            }
            case _ => throw new InvalidUnitInventoryException
        }
    }

    def movementToCurrent(operator:(Float,Float) => Float, unit:CompanyUnit)={
        this.amount.is.toFloat match {
            case (amount:Float) if(amount >= 0) => {
                    val stock = operator(InventoryCurrent.currentStock(productObj,unit), amount)
                    InventoryCurrent.currentStock(stock, productObj, unit)
            }
            case _ =>  throw new InvalidAmountInventoryException
        }
    }
    def ajustProductPrice {
        val prod = productObj
        if(prod.purchasePrice.is < this.purchasePrice.is){
            prod.purchasePrice(this.purchasePrice.is).save
        }
    }
    def save(location:CompanyUnit) = {
        this.product.obj match {
            case Full(product) => {
                beforeStock(BigDecimal(product.currentStock(location)))
            }
            case _ => throw new InvalidProductInventoryException 
        }
                   
        this.typeMovement.is match {
            case (t) if(t == InventoryMovement.In) => {
                if (causeObj.id == 9) {
                    println ("vaiii ===================== solicitacao");
                } else {
                    movementToCurrent(+=,location)
                    ajustProductPrice
                    averagePrice (productObj, unitObj, this.beforeStock.is.toFloat + this.amount.is.toFloat)
                }
            }
            case _ => {
                validateAmount(location)
                movementToCurrent(-=,location)
            }
        }
        super.save
    }

    def averagePrice [T <: code.model.ProductMapper[T]](product:ProductMapper[T], unit:CompanyUnit, qtd:Float) = {
        val inventoryMovementList = InventoryMovement.
            findAllInCompany (By(InventoryMovement.product,product.id.is),
             By(InventoryMovement.unit,unit.id.is),
             By(InventoryMovement.inventoryCause,AuthUtil.company.inventoryCausePurchase),
             OrderBy(InventoryMovement.efetiveDate, Descending)
             )
        var qtdAux:BigDecimal = this.amount.is;
        var priceAux:BigDecimal = this.purchasePrice.is * this.amount.is
        var indic1Aux:BigDecimal = this.indic1.is * this.amount.is
        var indic2Aux:BigDecimal = this.indic2.is * this.amount.is
        var indic3Aux:BigDecimal = this.indic3.is * this.amount.is
        var indic4Aux:BigDecimal = this.indic4.is * this.amount.is
        var indic5Aux:BigDecimal = this.indic5.is * this.amount.is
        inventoryMovementList.foreach((im) => {
            if (qtdAux <= qtd) {
                //println ("vaiii OK ================ " + im.efetiveDate + " " + product.id.is + " " + im.product + " ==== " + im.amount + " === " + im.purchasePrice + " ==== " + im.inventoryCause)
                priceAux = priceAux + (im.amount * im.purchasePrice)
                indic1Aux = indic1Aux + (im.amount * im.indic1)
                indic2Aux = indic2Aux + (im.amount * im.indic2)
                indic3Aux = indic3Aux + (im.amount * im.indic3)
                indic4Aux = indic4Aux + (im.amount * im.indic4)
                indic5Aux = indic5Aux + (im.amount * im.indic5)
                qtdAux = qtdAux + im.amount
            } else {
                //println ("vaiii OK nao mais ================ " + im.efetiveDate + " "  + product.id.is + " " + im.product + " ==== " + im.amount + " === " + im.purchasePrice + " ==== " + im.inventoryCause)
            }
        })
        //println ("vaiii ====================== fim " + (priceAux / qtdAux));
        InventoryCurrent.currentStockAverage (priceAux/qtdAux, indic1Aux/qtdAux, indic2Aux/qtdAux,
        indic3Aux/qtdAux,indic4Aux/qtdAux,indic5Aux/qtdAux,productObj, unit)
        (priceAux / qtdAux)
    }

    def validateAmount(location:CompanyUnit) = this.product.obj match {
        case Full(product) => {
            lazy val stock:Float = product.currentStock(location)toFloat
            def amountMovent:Float = this.amount.is.toFloat
            if(stock >= amountMovent || product.allow_negative_inventory_?.is){
                true
            }else{
                throw new InsufficientInventoryException(product, location)
            }
        }
        case _ => throw new InvalidProductInventoryException()
    }
    def product_name = {
        product.obj match {
            case Full(p) => p.name.is
            case _ => ""
        }
    }
    def bp_name = {
        business_pattern.obj match {
            case Full(p) => p.short_name.is
            case _ => ""
        }
    }
}

object InventoryMovement extends InventoryMovement with LongKeyedMapperPerCompany[InventoryMovement]  with  OnlyCurrentCompany[InventoryMovement] {

    def SQL_REPORT_INVENTORY = """
    select pr.external_id, pr.barcode, pr.name, pf.name, u.name, ic.currentstock 
    from product pr
    inner join inventorycurrent ic on ic.product = pr.id
    inner join companyunit u on u.id = ic.unit
    left join business_pattern pf on pf.id = pr.brand
    where pr.company = ? and ic.unit=?
    and pr.productclass = 1 and pr.is_inentory_control = true
    and pr.status = 1
    --and ic.currentstock > 0
    and %s and %s and %s
    order by u.name, pr.brand, pr.name
    """
    def SQL_REPORT_INVENTORY_UNIT_NO_FILTER = """
    select pr.external_id, pr.barcode, pr.external_id||' - '||pr.name, pf.name, u.name, ic.currentstock 
    from product pr
    inner join inventorycurrent ic on ic.product = pr.id
    inner join companyunit u on u.id = ic.unit
    left join business_pattern pf on pf.id = pr.brand
    where pr.company = ?
    and pr.productclass = 1 and pr.is_inentory_control = true
    and pr.status = 1
    --and ic.currentstock > 0
    and %s and %s and %s
    order by pr.name, u.name, pr.brand
    """    

    object InventoryMovementType extends Enumeration {
            type InventoryMovementType = Value
            val In, Out = Value
    }

    object InventoryLocation extends Enumeration {
            type InventoryLocation = Value
            val Inventory, Home = Value
    }        

    def remove(amount:Float) = {
        InventoryMovement.createInCompany
                .amount(BigDecimal(amount))
                .typeMovement(InventoryMovement.InventoryMovementType.Out)
    }

    def add(purchaseprice:Double, indic1:Double, indic2:Double, indic3:Double, indic4:Double, indic5:Double,
        saleprice:Double, amount:Float) = {
        InventoryMovement.createInCompany
                         .amount(BigDecimal(amount))
                         .typeMovement(InventoryMovement.InventoryMovementType.In)
                         .purchasePrice(purchaseprice)
                         .indic1(indic1).indic2(indic2).indic3(indic3).indic4(indic4).indic5(indic5)
                         .salePrice(saleprice)
    }

    def Inventory = InventoryMovement.InventoryLocation.Inventory

    def In = InventoryMovement.InventoryMovementType.In
}