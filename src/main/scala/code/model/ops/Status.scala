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


trait StatusConstants{
  val STATUS_OK = 1
  val STATUS_BLOCKED = 2
  val STATUS_DELETED = 3
  val STATUS_INACTIVE = 4
}
object StatusConstants extends StatusConstants{

}