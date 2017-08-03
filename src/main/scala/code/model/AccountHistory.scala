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


class AccountHistory extends LongKeyedMapper[AccountHistory] 
  with IdPK with CreatedUpdated with CreatedUpdatedBy 
  with PerCompany with PerUnit {
  def getSingleton = AccountHistory
  object description extends MappedPoliteString(this, 455)
  object account extends MappedLongForeignKey(this, Account) {
    override def dbIndexed_? = true
  }
  object accountPayable extends MappedLongForeignKey(this, AccountPayable) {
    override def dbIndexed_? = true
  }
  object currentValue extends MappedCurrency(this)
  object value extends MappedCurrency(this)
}
object AccountHistory extends AccountHistory with LongKeyedMapperPerCompany[AccountHistory]
