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


trait Siteble extends Imageble{

  self: BaseMapper =>
  object allowShowOnSite_? extends MappedBoolean(this.asInstanceOf[MapperType]){
    override def defaultValue = false
    override def dbColumnName = "allowshowonsite"
  }

  object allowShowOnPortal_? extends MappedBoolean(this.asInstanceOf[MapperType]){
    override def defaultValue = false
    override def dbColumnName = "allowshowonportal"
  }

  object moderatedPortal_? extends MappedBoolean(this.asInstanceOf[MapperType]){
    override def defaultValue = false
    override def dbColumnName = "moderatedportal"
  }
  object siteDescription extends MappedPoliteString(this.asInstanceOf[MapperType],4000)

  object siteTitle extends MappedPoliteString(this.asInstanceOf[MapperType], 255)  with LifecycleCallbacks{
    override def beforeSave() {
      this.set(BusinessRulesUtil.toCamelCase(this.is))
    }
  }
}