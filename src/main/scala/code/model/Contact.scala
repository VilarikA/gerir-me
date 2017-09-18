package code
package model

import code.util._

import net.liftweb._
import mapper._
import http._
import SHtml._
//import util._
import _root_.java.math.MathContext;
import scalendar._
import Month._
import Day._
import net.liftweb.common.{ Box, Full, Empty }
import net.liftweb.proto._

import java.util.Date
import java.util.Calendar

class Contact extends LongKeyedMapper[Contact] 
  with PerCompany 
  with PerUnit
  with IdPK {

  def getSingleton = Contact

  object origin extends MappedPoliteString(this,255)

  object name extends MappedPoliteString(this,255)

  object birthday extends EbMappedDate(this){
    override def toLong: Long = is match {
      case null => 0l//(2206303200000l)*(-1)
      case d: java.util.Date => d.getTime
    }
  }

  object email extends MappedPoliteString(this,150) with LifecycleCallbacks {
    override def beforeSave() {
        super.beforeSave;
        if(this.get != ""){
          this.get.toLowerCase.trim.split(",|;").foreach((email1) => {
            if (!emailPattern.matcher(email1).matches /*&& !isNew*/) {
              throw new RuntimeException("E-mail inválido! " + email1)
            }
          })
          this.set(this.get.toLowerCase.trim)
        }
    } 
    def emailPattern = ProtoRules.emailRegexPattern.vend
  }
  object phone extends MappedPoliteString(this,20)  with LifecycleCallbacks {
    override def beforeSave() {
        super.beforeSave;
//          if(this.get == "(31) "){
        if(this.get.length < 8){
          this.set("")
        }
    } 
  }
  object mobilePhone extends MappedPoliteString(this,20) with LifecycleCallbacks {
    override def dbColumnName = "mobile_phone"
      override def beforeSave() {
        super.beforeSave;
//          if(this.get == "(31) "){
        if(this.get.length < 8){
          this.set("")
        }
      } 
  }

  object date1 extends EbMappedDate(this){
    override def toLong: Long = is match {
      case null => 0l//(2206303200000l)*(-1)
      case d: java.util.Date => d.getTime
    }
  }

  object value extends MappedCurrency(this) with LifecycleCallbacks {
    override def defaultValue = 0.0;
  }

  object obs extends MappedPoliteString(this,255)

  object business_pattern extends MappedLong(this)

  def makeAsCustomer = {
    if (business_pattern.is == 0 || business_pattern == null) {
      val ac = Customer.createInCompany
      .name (name)
      .email (email)
      .mobilePhone (phone)
      .birthday (birthday)
      .obs (obs + " " + origin)
      try {
        ac.save
      } catch {
        case e:Exception => {
          println ("vaiiiii ==== " + e.getMessage)
          throw e
        }
      }
    } else {
      val ac = Customer.findByKey(business_pattern).get
      if (ac.email == "" && email != "") {
        ac.email (email)
      }
      if (ac.phone == "" && phone != "") {
        ac.phone (phone)
      }
      try {
        ac.save
      } catch {
        case e:Exception => {
          println ("vaiiiii ==== " + e.getMessage)
          throw e
        }
      }
    }
    this.delete_!
  }
}

object Contact extends Contact with LongKeyedMapperPerCompany[Contact]


