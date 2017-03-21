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

class Relationship extends Audited[Relationship] with PerCompany with IdPK 
    with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[Relationship] with ActiveInactivable[Relationship] { 
    def getSingleton = Relationship
    override def updateShortName = false
    object relationshipType extends  MappedLongForeignKey(this, RelationshipType)
    object relationshipReverse extends MappedLongForeignKey(this, Relationship)
    object obs extends MappedPoliteString(this,255)

    object female_name extends MappedPoliteString(this,255) with LifecycleCallbacks {
      override def beforeSave() {
          super.beforeSave;
          this.set(BusinessRulesUtil.toCamelCase(this.is))
      }      
    }  
    
    object female_search_name extends MappedPoliteString(this,255)
    object female_short_name extends MappedPoliteString(this,20)

    object generic_name extends MappedPoliteString(this,255) with LifecycleCallbacks {
      override def beforeSave() {
          super.beforeSave;
          this.set(BusinessRulesUtil.toCamelCase(this.is))
      }      
    }  
    
    object generic_search_name extends MappedPoliteString(this,255)
    object generic_short_name extends MappedPoliteString(this,20)

    object orderInreport extends MappedInt(this){
        override def defaultValue = 20
    }
    def relationshipTypeName = relationshipType.obj match {
        case Full(t) => t.name.is
        case _ => ""
    }

    def hasReverseRelationship:Boolean = relationshipReverse.obj match {
        case Full(a) => true
        case _ => false
    }
}

object Relationship extends Relationship with LongKeyedMapperPerCompany[Relationship]  with  OnlyActive[Relationship]{
    
}