package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import code.util._
import code.service._
import java.util.Calendar
import net.liftweb.common.{Box,Full}

class UnitPartner extends BusinessPattern[UnitPartner]{
  def getSingleton = UnitPartner
  override val isPersonalName_? = false
  override val isUnitDefaultValue_? = true
  override def defaultValueIsCustomer = false
  override def cityDefaultValue:String = ""
  override def stateDefaultValue:String = ""
  override def cityrefDefaultValue:Long = 1821 //"Belo Horizonte"
  override def staterefDefaultValue:Long = 1 //"MG"

}
object UnitPartner extends UnitPartner with BusinessPatternMeta[UnitPartner]