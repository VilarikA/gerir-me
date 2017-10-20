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

trait CreatedByTrait {
  self: BaseMapper =>

  import net.liftweb.util._

  protected def createdByIndexed_? = false

  lazy val createdBy = new MyCreatedBy(this)

  protected class MyCreatedBy(obj: self.type) extends MappedLongForeignKey(obj.asInstanceOf[MapperType],User) {
    override def defaultValue = AuthUtil.userId
    override def dbIndexed_? = createdByIndexed_?
  }

  lazy val createdByName = createdBy.obj match {
        case Full(t:User) => t.short_name.is
        case _ => ""
  }
}

trait UpdatedByTrait {
  self: BaseMapper =>

  import net.liftweb.util._

  protected def updatedByIndexed_? = false

  lazy val updatedBy: MyUpdatedBy = new MyUpdatedBy(this)

  protected class MyUpdatedBy(obj: self.type) extends MappedLongForeignKey(obj.asInstanceOf[MapperType],User) with LifecycleCallbacks {
    override def beforeSave() {
      super.beforeSave; 
      if(AuthUtil.userId != 0){
        this.set(AuthUtil.userId)
      }
    }
    override def defaultValue = AuthUtil.userId
    override def dbIndexed_? = updatedByIndexed_?
  }

  lazy val updatedByName = updatedBy.obj match {
        case Full(t:User) => t.short_name.is
        case _ => ""
  }
}

trait CreatedUpdatedBy extends CreatedByTrait with UpdatedByTrait {
  self: BaseMapper =>
}