package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import code.util._
import json._
import net.liftweb.common.{Box,Full}

import java.util.Date

class AgeRange extends Audited[AgeRange] with KeyedMapper[Long, AgeRange] with BaseLongKeyedMapper
     with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy with NameSearchble[AgeRange] 
     with ActiveInactivable[AgeRange] {
    def getSingleton = AgeRange
    override def updateShortName = false

	object obs extends MappedString(this, 4000)
}

object AgeRange extends AgeRange with LongKeyedMapperPerCompany[AgeRange] with OnlyActive[AgeRange]{
}

class AgeRangeInterval extends Audited[AgeRangeInterval] with KeyedMapper[Long, AgeRangeInterval] with BaseLongKeyedMapper
     with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy
     with ActiveInactivable[AgeRangeInterval] {
    def getSingleton = AgeRangeInterval

    object agerange extends MappedLong(this)
    
    object name extends MappedString(this, 255) with LifecycleCallbacks {
      override def beforeSave() {
          super.beforeSave;
          if(this.get == ""){
          } else {
            this.set(BusinessRulesUtil.toCamelCase(this.is))
          }
      }      
    }  

    object startmonths extends MappedInt(this)
    object endmonths extends MappedInt(this)
    object obs extends MappedString(this, 4000)
}

object AgeRangeInterval extends AgeRangeInterval with LongKeyedMapperPerCompany[AgeRangeInterval] with OnlyCurrentCompany[AgeRangeInterval]

