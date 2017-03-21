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

class SqlCommand extends Audited[SqlCommand] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy with NameSearchble[SqlCommand] with ActiveInactivable[SqlCommand] {
  def getSingleton = SqlCommand
  object sqlcmd extends MappedPoliteString(this, 4000)
  object obs extends MappedPoliteString(this, 255)

}

object SqlCommand extends SqlCommand with LongKeyedMapperPerCompany[SqlCommand] with OnlyActive[SqlCommand] {
}



