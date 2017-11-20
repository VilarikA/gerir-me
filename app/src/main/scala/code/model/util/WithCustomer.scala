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

trait WithCustomer { 
  self: BaseMapper =>
  object customer extends MappedLongForeignKey(this.asInstanceOf[MapperType],Customer){
    override def dbIndexed_? = true
  }
  object customerOrigin extends MappedLongForeignKey(this.asInstanceOf[MapperType],Customer){
    override def dbIndexed_? = true
    override def defaultValue = self.customer.is
  }     
}