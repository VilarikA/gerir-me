
package code
package model 

import net.liftweb._ 
import scala.xml._
import mapper._ 
import http._ 
import SHtml._ 
import util._
import code.util._
import _root_.java.math.MathContext
import net.liftweb.mapper.DB
import net.liftweb.common._
import java.util.Date


class CommisionDetails extends LongKeyedMapper[CommisionDetails] with PerCompany with IdPK with CreatedUpdated{
  def getSingleton = CommisionDetails
  object commision extends MappedLongForeignKey(this,Commision)
  object description extends MappedPoliteString(this,400)
  object value extends MappedCurrency(this)
}

object CommisionDetails extends CommisionDetails with LongKeyedMapperPerCompany[CommisionDetails]  with  OnlyCurrentCompany[CommisionDetails]{

}
