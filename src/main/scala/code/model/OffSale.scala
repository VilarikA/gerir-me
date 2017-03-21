package code
package model 

/*
import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import json._
*/

import net.liftweb._
import mapper._
import code.actors._
import http._
import SHtml._
import util._
import code.util._
import net.liftweb.mapper.{ StartAt, MaxRows, NotBy }
import java.util.regex._
import java.util.Date
import scala.xml.Text
import net.liftweb.proto._
import net.liftweb.common._
import net.liftweb.json._

import _root_.java.math.MathContext

//import java.util.Date

class OffSale extends Audited[OffSale] with KeyedMapper[Long, OffSale] 
    with BaseLongKeyedMapper with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy 
	with NameSearchble[OffSale] 
    with ActiveInactivable[OffSale] 
    with CompanyIdable[OffSale] with Siteble {
    def getSingleton = OffSale
    override def updateShortName = false
    def imagePath = "offsale"

    object startAt extends EbMappedDateTime(this) {
        override def defaultValue = new Date()
    }
    object endAt extends EbMappedDateTime(this)
    object percentOff extends MappedDouble(this)
    object limitedValue_? extends MappedBoolean(this){
    	override def dbColumnName = "limited_value"
    }
    object value extends MappedDouble(this){
    }
    object usedValue extends MappedDouble(this){
    }
    object changePrice_? extends MappedBoolean(this){
    	override def dbColumnName = "change_price"
    }
    object preservCommission_? extends MappedBoolean(this){
    	override def dbColumnName = "preserv_commission"
    }
	object validDays extends MappedString(this, 30)    
	object iniHour extends MappedString(this, 10)
	object endHour extends MappedString(this, 10)
	object obs extends MappedString(this, 400)
	object notification extends MappedLongForeignKey(this, NotificationMessage)
	object offType extends MappedInt(this)
    object partner extends MappedLongForeignKey(this, OffSalePartner)
    object external_id extends MappedPoliteString(this,200)
    object document_ans extends MappedPoliteString(this,20) // numero no conveio na ANS
    object xmlname extends MappedPoliteString(this,20) // abreviação para aqruivo xml
    object indic1 extends MappedDecimal(this,MathContext.DECIMAL64,2) {// defaults para offsale product 
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

    def updateFromJson(json: JsonAST.JObject) = {
        json.values.map((value) =>{
            this.fieldByName(value._1).get.set(value._2)
        })
        this.save
    }
    lazy val getPartner: OffSalePartner = {
        partner.obj match {
          case Full(currentPartner) => currentPartner
          case _ => {
            val offsalePartner = OffSalePartner.createInCompany
            partner(offsalePartner)
            offsalePartner
          }
        }
    }
    override def save() = {
        //      getPartner.name(BusinessRulesUtil.clearString(this.name.is)).save
        getPartner.company(this.company).name(this.name + " offsale pattern").
        offsale(this.id).is_unit_?(false).is_member_?(false).save
        super.save()
    }
}

object OffSale extends OffSale with LongKeyedMapperPerCompany[OffSale] with OnlyActive[OffSale]{
    
    def createFromJson(json: JsonAST.JObject) = decodeFromJSON_!(json, true)
}
