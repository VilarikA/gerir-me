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

trait ActiveInactivable [self <: net.liftweb.mapper.Mapper[self]] extends StatusConstants {
    self: BaseMapper with ActiveInactivable [self] with PerCompany =>
    object status extends MyStatus(this.asInstanceOf[self]){
      override def dbIndexed_? = true
      override def defaultValue = StatusConstants.STATUS_OK
    }
    protected class MyStatus(obj: self) extends MappedInt(obj)


    def isInactive = this.status.is == StatusConstants.STATUS_INACTIVE
    def isActive = this.status.is == StatusConstants.STATUS_OK
  }

  trait OnlyActive[A <: LongKeyedMapper[A]] extends OnlyCurrentCompany[A]  {
    self: A with OnlyActive[A] with PerCompany with ActiveInactivable[A] with MetaMapper[A] => 
    override def findAllInCompanyOrDefaultCompany:List[A] = findAllInCompanyOrDefaultCompany()
    override def findAllInCompanyOrDefaultCompany(params: QueryParam[A]*): List[A] = {
      super.findAllInCompanyOrDefaultCompany(By(self.status, StatusConstants.STATUS_OK) :: params.toList :_*)  
    }
    override def findAllInCompany:List[A] = findAllInCompany()
    override def findAllInCompany(params: QueryParam[A]*): List[A] = {
      super.findAllInCompany(By(self.status, StatusConstants.STATUS_OK) :: params.toList :_*)
    }

    def findAllInCompanyWithInactive(params: QueryParam[A]*): List[A] = {
      super.findAllInCompany(params.toList :_*)
    }  

    override def countInCompany(params: QueryParam[A]*): Long = {
      super.countInCompany(By(self.status, StatusConstants.STATUS_OK) :: params.toList :_*)
    }  
  }
