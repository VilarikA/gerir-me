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

class RelationshipType extends Audited[RelationshipType] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[RelationshipType] with ActiveInactivable[RelationshipType]{ 
    def getSingleton = RelationshipType
    override def updateShortName = false
    object obs extends MappedPoliteString(this,255)
    object orderInreport extends MappedInt(this){
        override def defaultValue = 10
    }
}

object RelationshipType extends RelationshipType with LongKeyedMapperPerCompany[RelationshipType]  
    with  OnlyActive[RelationshipType]
