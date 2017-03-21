package code
package model

import code.util._

import net.liftweb._
import mapper._
import http._
import SHtml._
import util._
import _root_.java.math.MathContext;
import scalendar._
import Month._
import Day._
import net.liftweb.common.{ Box, Full, Empty }

import java.util.Date
import java.util.Calendar

trait FinancialMovement extends PerUnit {
  self: BaseMapper =>
  object typeMovement extends MappedInt(this.asInstanceOf[MapperType]) {
    override def dbIndexed_? = true
    override def defaultValue = AccountPayable.IN
  }
  object user extends MappedLongForeignKey(this.asInstanceOf[MapperType], User)
  object account extends MappedLongForeignKey(this.asInstanceOf[MapperType], Account)
  object category extends MappedLongForeignKey(this.asInstanceOf[MapperType], AccountCategory) {
    override def dbIndexed_? = true
  }
  object value extends MappedCurrency(this.asInstanceOf[MapperType])
  def realValue:Double = if (typeMovement.is == AccountPayable.OUT) {
    value.is * (-1.00)
  } else {
    value.is
  }
  object invoice extends MappedPoliteString(this.asInstanceOf[MapperType], 200) {
    override def dbIndexed_? = true
  } //Nota fiscal
  object costCenter extends MappedLongForeignKey(this.asInstanceOf[MapperType], CostCenter) {
    override def dbIndexed_? = true
  }
  object amount extends MappedCurrency(this.asInstanceOf[MapperType]) {
      override def defaultValue = 1l
  }
  object parcelTot extends MappedInt(this.asInstanceOf[MapperType]) {
    override def defaultValue = 1
  }
  
  object obs extends MappedPoliteString(this.asInstanceOf[MapperType], 255)
  object paymentType extends MappedLongForeignKey(this.asInstanceOf[MapperType], PaymentType) {
    override def dbIndexed_? = true
  }

}