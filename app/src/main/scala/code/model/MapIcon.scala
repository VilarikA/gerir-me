package code
package model 

import net.liftweb._
import mapper._
import code.actors._
import http._
import SHtml._
import util._
import code.util._
import net.liftweb.mapper.{ StartAt, MaxRows, NotBy }
import java.util.regex._
import java.util.Date
import scala.xml.Text
import net.liftweb.proto._
import net.liftweb.common._
import net.liftweb.json._

import _root_.java.math.MathContext; 

class MapIcon extends Audited[MapIcon] with PerCompany with IdPK with Imageble with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[MapIcon] with ActiveInactivable[MapIcon]{ 
    def getSingleton = MapIcon
    override def updateShortName = false
    object iconPath extends MappedPoliteString(this,455)
	object obs extends MappedString(this, 4000)

    def imagePath = "mapicon"

}

object MapIcon extends MapIcon with LongKeyedMapperPerCompany[MapIcon]  with  OnlyActive[MapIcon] {
	def findAllInCompanyOrDefaultCompanyMapicon = findAll(OrderBy(MapIcon.name, Ascending),By(MapIcon.status, Company.STATUS_OK),
		BySql("company in (?,26)", IHaveValidatedThisSQL("", ""), AuthUtil.company.id.is.toLong),
		BySql("name not in (select m1.name from mapicon m1 where m1.company = ? and m1.status <> 1)", IHaveValidatedThisSQL("", ""),AuthUtil.company.id.is.toLong)
		)
}
