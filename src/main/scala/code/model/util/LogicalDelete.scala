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

trait  LogicalDelete[OwnerType <: LongKeyedMapper[OwnerType]] extends LongKeyedMapper[OwnerType] with Audited[OwnerType]{
    self: OwnerType with PerCompany with IdPK with Mapper[OwnerType] =>

    lazy val deleted_? : MyDeleted = new MyDeleted(this)

    protected class MyDeleted(obj: self.type) extends MappedBoolean(obj.asInstanceOf[OwnerType]){
      override def defaultValue = false
      override def dbColumnName = "deleted"
      override def dbIndexed_? = true
    }
    def insecuriDelete_! = {
      super.delete_!
    }
    override def delete_! = {
      val ret = this.deleted_?(true).save
      audit("afterLogicalDelete")
      ret
    }
}