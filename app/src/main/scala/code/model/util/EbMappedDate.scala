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

abstract class EbMappedDate [T<:Mapper[T]](override val fieldOwner: T) extends MappedDate[T](fieldOwner){
  override def asJsExp: JsExp = if(toLong == 0l) JsNull else Num(toLong)
}
abstract class EbMappedDateTime [T<:Mapper[T]](override val fieldOwner: T) extends MappedDateTime[T](fieldOwner){
  override def asJsExp: JsExp = if(toLong == 0l) JsNull else Num(toLong)
}
