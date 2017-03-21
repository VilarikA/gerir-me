package code
package model 

import net.liftweb._ 
import mapper._ 
import net.liftweb.util.{FieldError}
import code.util
import code.actors._
import http._ 
import SHtml._ 
import util._
import net.liftweb.http.js._ 
import json._

trait Restable[A <: Mapper[A]] extends net.liftweb.common.Logger  {
    self: Mapper[A] =>
    lazy val NOT_UPDATE_BY_JSON = "updatedAt" :: "createdAt" :: "id" :: "createdBy" :: "updatedBy" :: Nil

    def updateFromJson(json: JsonAST.JObject) = {
        json.values.map((value) =>{
            if(!NOT_UPDATE_BY_JSON.exists( _ == value._1)){
                def valueToSet = value._2 match{
                    case v:BigInt => v.toLong
                    case _ => value._2
                }
                try{
//                    info(" Name : "+value._1)
//                    info(" value : "+value._2)
                    val field:BaseMappedField = this.fieldByName(value._1).get
                    field match {
                        case f:MappedDateTime[A] => {
                            if(valueToSet != null){
                                f.set(new java.util.Date(valueToSet.asInstanceOf[Long]))
                            }else{
                                f.set(f.defaultValue)
                            }
                        }
                        case f:MappedDate[A] => {
                            if(valueToSet != null){
                                f.set(new java.util.Date(valueToSet.asInstanceOf[Long]))
                            }else{
                                f.set(f.defaultValue)
                            }
                        }
                        case f:MappedInt[A] => field.set(valueToSet.toString.toInt.asInstanceOf[field.ValueType])
                        case f:MappedCurrency[A] => field.set(valueToSet.toString.toDouble.asInstanceOf[field.ValueType])
                        case f:MappedDecimal[A] => field.set(BigDecimal(valueToSet.toString).asInstanceOf[field.ValueType])
                        case _ => field.set(valueToSet.asInstanceOf[field.ValueType])
                    }
                }catch{
                    case e:NoSuchElementException => {
                    }
                    case e:Exception => {
                        throw e 
                    }
                }
            }
        })
        this.save
    }

}

trait  MetaRestable [A <: LongKeyedMapper[A]]{
    self: A with MetaRestable[A] with MetaMapper[A] => 
    def createFromJson(json: JsonAST.JObject) = {
        val obj = self.create
        obj.asInstanceOf[Restable[A]].updateFromJson(json)
        obj.asInstanceOf[PerCompany].company(AuthUtil.company)
    }
}