package code
package comet

import net.liftweb._
import http._
import actor._
import util._
import Helpers._
import http.js._
import JE._
import JsCmds._
import code.util._
import code.service._  
import code.model._
import net.liftweb.common.{Box,Full,Empty}
import java.util.Date

object TratmentServer extends LiftActor with ListenerManager with net.liftweb.common.Logger {
	var createUpdate:TreatmentMessage = null
  override def lowPriority = {
    case s:TreatmentMessage => {
      createUpdate = s
      updateListeners()    
    }
  }
}
class TrasTratmentServerListener extends CometActor with CometListener with net.liftweb.common.Logger {
  def registerWith = TratmentServer
  var createUpdate = None : Option[TreatmentMessage]
  override def lowPriority = {
    case (a:TreatmentMessage) => {
      createUpdate = Some(a)
      //info(" Company 1 "+AuthUtil.company.id.is + " Company 2 "+a.company)
      //info(" Company2"+a.company)
       if(AuthUtil.company.id.is ==a.company && a.unit == AuthUtil.unit.id.is )
            reRender()
    }
  }

  def render()= {
    createUpdate match {      
      case Some(value) => Script(Call("refreshCalendarByAjax", value.date.getTime))
      case None => Noop
    }    
  }
}
case class TreatmentMessage(message:String, date:Date, company:Long = AuthUtil.company.id.is, unit:Long = AuthUtil.unit.id.is);