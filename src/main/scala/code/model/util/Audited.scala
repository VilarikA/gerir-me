package code
package model 

import net.liftweb.http.js.JE._
import net.liftweb.http.js.JsExp
import net.liftweb._ 
import mapper._ 
import util._ 
import net.liftweb.util.{FieldError}
import code.util

import http._ 
import SHtml._ 
import util._
import util._ 

import net.liftweb.common.{Box,Full,Empty,Failure,ParamFailure}

trait Audited [OwnerType <: LongKeyedMapper[OwnerType]] extends LongKeyedMapper[OwnerType]{
    self: OwnerType with PerCompany with IdPK with Mapper[OwnerType] =>

    def audit(event:String){
      AuditMapper
      .create
      .company(self.company)
      .idObj(self.id)
      .jsObj(self.asJs.toJsCmd)
      .table(self.getSingleton.dbTableName)
      .event(event)
      .save
    }
    def isNew = this.id.is match {
      case p:Long if(p > 0) => false
      case _ => true
    }
    
    override def save = {
    // trouxe para cá estava depois do audit e o id era -1 no insert - rigel 26/07/2014
    val ret = super.save
    if(isNew){
      audit("afterInsert")
      }else{
        audit("afterSave")
      }
      ret
    }
    override def delete_! = {
      val ret = super.delete_!
      audit("afterDelete")
      ret

    }
  }