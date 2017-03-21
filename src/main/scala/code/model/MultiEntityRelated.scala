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

trait MultiEntityRelated {
  self: BaseMapper =>
  object idObj extends MappedLong(this.asInstanceOf[MapperType])
  object table extends MappedPoliteString(this.asInstanceOf[MapperType],200)  
}
