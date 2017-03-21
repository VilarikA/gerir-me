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

trait SitebleMapper[A <: LongKeyedMapper[A]] extends Siteble{
  self: A with MetaMapper[A] with Siteble =>
  def findAllToSite:List[A] = findAll(By(self.allowShowOnPortal_?, true), By(self.moderatedPortal_?, true))
}
