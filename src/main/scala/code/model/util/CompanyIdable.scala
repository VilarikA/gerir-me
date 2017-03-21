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

trait CompanyIdable [self <: net.liftweb.mapper.Mapper[self]] {
    self: BaseMapper with CompanyIdable [self] with PerCompany =>

    protected def updateIdForCompany = true;

    object idForCompany extends MyId(this.asInstanceOf[self])  with LifecycleCallbacks {
      override def defaultValue = 0
      override def beforeSave() {
        super.beforeSave;
        if(this.is == 0 && updateIdForCompany) {
          this.set(nextIdForCompany)
        }
      }
      override def dbIndexed_? = true
    }
    protected class MyId(obj: self) extends MappedInt(obj){
    }
    def nextIdForCompany:Int = {
      try{
        val ownerTyped = self.asInstanceOf[self with PerCompany]
        val r = DB.performQuery("select max(idforcompany) from "+ownerTyped.getSingleton.dbTableName+" where company = ?", List(this.company.is))
        r._2(0)(0) match {
         case a:Any => a.toString.toInt+1
         case _ => 1
       }
       }catch{
        case e:Exception =>{
          return 1
        }
      }
  }
}
