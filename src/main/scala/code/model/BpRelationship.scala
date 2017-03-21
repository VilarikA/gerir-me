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

class BpRelationship extends Audited[BpRelationship] with KeyedMapper[Long, BpRelationship] with BaseLongKeyedMapper
     with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy 
     with ActiveInactivable[BpRelationship] {
    def getSingleton = BpRelationship

    object business_pattern extends MappedLongForeignKey(this, Customer)
    object bp_related extends MappedLongForeignKey(this, Customer)
    object startAt extends EbMappedDate(this) {
        override def defaultValue = new Date()
    }
    object endAt extends EbMappedDate(this)
	object obs extends MappedString(this, 4000)
    object relationship extends  MappedLongForeignKey(this, Relationship)
    object reverse extends  MappedLongForeignKey(this, Relationship)
    object lastRelationship extends MappedLong(this) with LifecycleCallbacks {
        override def defaultValue = fieldOwner.relationship
        override def beforeSave() {
          super.beforeSave;
          this.set(this.defaultValue);
        }
    }

    def bpName = business_pattern.obj match {
        case Full(t) => t.name.is
        case _ => ""
    }

    def relatedName = bp_related.obj match {
        case Full(t) => t.name.is
        case _ => ""
    }

    def relationshipName = relationship.obj match {
        case Full(t) => t.name.is
        case _ => ""
    }

    def clearReverseRelationship {
        BpRelationship.findAll(By(reverse, this.id.is)).foreach( _.deleteWithouReverseRelationship)
    }
    def addReverseRelationship {
        if(relationship.obj.get.hasReverseRelationship){
            val relationshipReverse = relationship.obj.get.relationshipReverse.is
            val reverseRelation = BpRelationship.createInCompany
            .business_pattern(bp_related)
            .bp_related(business_pattern)
            .relationship(relationshipReverse)
            .reverse(this.id.is)
            .startAt(startAt)
            .endAt(endAt)
            .obs(obs)
            .saveWithouReverseRelationship
            this.reverse(reverseRelation.id.is).saveWithouReverseRelationship
        }
    }
    def deleteWithouReverseRelationship = super.delete_!
    override def delete_! = {
        clearReverseRelationship
        super.delete_!
    }
    override def save = {
        val isNew = this.id.is match {
            case p:Long if(p > 0) => false
            case _ => true
        }
        val lr = lastRelationship.is
        val r = super.save
        if(isNew || lr != relationship.is){
            clearReverseRelationship
            addReverseRelationship
        }
        r
    }
    def saveWithouReverseRelationship = {
       super.save
       this
    }
    //relationshipReverse

    def relationshipByBP (bpId:Long, bpRelated:Long, relationship:Long) = {
        BpRelationship.count(
                            By(BpRelationship.business_pattern, bpId), 
                            By(BpRelationship.bp_related, bpRelated),
                            By(BpRelationship.relationship, relationship)
                        )
    }

    def addBpRelationship(bpId:Long, bpRelated:Long, relationship:Long){
        val stakes = BpRelationship.relationshipByBP(bpId, bpRelated, relationship)
        if (stakes > 0) {
        } else {
            BpRelationship
                .createInCompany
                .relationship(relationship)
                //.startAt(this.startAt)
                //.endAt()
                .business_pattern(bpId)
                .bp_related(bpRelated)
                .save
        }
    }

}

object BpRelationship extends BpRelationship with LongKeyedMapperPerCompany[BpRelationship] with OnlyActive[BpRelationship]{
    def createFromJson(json: JsonAST.JObject) = decodeFromJSON_!(json, true)
}

