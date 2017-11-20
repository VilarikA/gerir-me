
package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._
import _root_.java.math.MathContext
import net.liftweb.mapper.DB
import java.util.Date;
import net.liftweb.json._
import http.js._
import JE._
import net.liftweb.common._
import code.util._

trait UserEvent {
	self: BaseMapper =>
	val INCREMENT_15_MINUTES = 15*60*1000L;	
	    object user extends MappedLongForeignKey(this.asInstanceOf[MapperType],User){
	    	override def dbIndexed_? = true
	    }
	    object start extends EbMappedDateTime(this.asInstanceOf[MapperType]){
	        override def defaultValue = Project.date_format_db.parse(Project.date_format_db.format(new Date()));
	    	override def dbIndexed_? = true
	    }
	    object end extends EbMappedDateTime(this.asInstanceOf[MapperType]) with LifecycleCallbacks{
	        override def defaultValue = Project.date_format_db.parse(Project.date_format_db.format(new Date()));
	    	override def dbIndexed_? = true
			override def beforeSave() {
			  super.beforeSave;
			  if (this.get.getTime <= start.is.getTime) {
				 this.set(new Date(start.is.getTime+(INCREMENT_15_MINUTES)))
			  }
			} 
	    }
	    object dateEvent extends EbMappedDate(this.asInstanceOf[MapperType])  with LifecycleCallbacks {
			override def beforeSave() {
			  super.beforeSave;
			  this.set(fieldOwner.asInstanceOf[UserEvent].start.is)
			} 
			override def dbIndexed_? = true	    	
	    }

	def between_query(start:Date, end:Date) = BySql("dateevent between date(?) and date(?)",IHaveValidatedThisSQL("",""),start,end)

	def strDescription:String
	def toJson:JsObj
	lazy val userNameAndThunb:String = {
		user.obj match {
            case Full(u)=> u.thumbAndName
            case _ => ""
        }
    }
    def maxConflictsAllowed = 1
}
