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

abstract class MappedCurrency [T<:Mapper[T]](override val fieldOwner: T) extends MappedDouble[T](fieldOwner) with LifecycleCallbacks {
  override def beforeSave() {
    this.set(BusinessRulesUtil.roundHalfUp(this.get.toDouble))
  }
}
