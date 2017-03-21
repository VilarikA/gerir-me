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

class Project1 extends Audited[Project1] with KeyedMapper[Long, Project1] with BaseLongKeyedMapper
     with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy with NameSearchble[Project1] 
     with ActiveInactivable[Project1] with PerUnit with CompanyIdable[Project1]
     with Siteble with net.liftweb.common.Logger {
    def getSingleton = Project1
    override def updateShortName = false

    object bp_sponsor extends MappedLong(this)
    object bp_manager extends MappedLong(this)
    object startAt extends EbMappedDateTime(this) {
        override def defaultValue = new Date()
    }
    object endAt extends EbMappedDateTime(this)
    object projectClass extends  MappedLongForeignKey(this, ProjectClass)
    object projectStage extends  MappedLongForeignKey(this, ProjectStage)
	object obs extends MappedString(this, 4000)
    object about extends MappedString(this, 4000)
    object goal extends MappedString(this, 4000)
    object audience extends MappedString(this, 4000)
    object schedule extends MappedString(this, 4000)
    object numberofguests extends MappedInt(this)
    object costCenter extends MappedLongForeignKey(this.asInstanceOf[MapperType], CostCenter) {
        override def dbIndexed_? = true
    }

    override def save = {
        val isNew = this.id.is match {
            case p:Long if(p > 0) => false
            case _ => true
        }
        val r = super.save
        //if(isNew){ agora faz sempre
        addStakeholder(bp_manager.is, StakeHolderType.MANAGER)
        addStakeholder(bp_sponsor.is, StakeHolderType.SPONSOR)
        //}
        r
    }

    def updateFromJson(json: JsonAST.JObject) = {
        json.values.map((value) =>{
            this.fieldByName(value._1).get.set(value._2)
        })
        this.save
    }
    def imagePath = "Project"

    def projectClassName = projectClass.obj match {
        case Full(t) => t.name.is
        case _ => ""
    }
    def removeStakeholder(idStaclHolder:Long) = {
        StakeHolder.findByKey(idStaclHolder).get.delete_!
    }
 
    def stakeholderByBP (project:Long, customer:Long, stakeholdertype:Long) = {
        StakeHolder.count(
                            By(StakeHolder.business_pattern, customer), 
                            By(StakeHolder.project, project),
                            By(StakeHolder.stakeHolderType, stakeholdertype)
                        )
    }

    def addStakeholder(bpId:Long, stakeholdertype:Long){
        val stakes = Project1.stakeholderByBP(this.id.is, bpId, stakeholdertype)
        if (stakes > 0) {
        } else {
            StakeHolder
                .createInCompany
                .project(this.id.is)
                .startAt(this.startAt)
                .endAt(this.endAt)
                .stakeHolderType(stakeholdertype)
                .business_pattern(bpId)
                .save
        }
    }

}

object Project1 extends Project1 with LongKeyedMapperPerCompany[Project1] with OnlyActive[Project1]{
    
    def createFromJson(json: JsonAST.JObject) = decodeFromJSON_!(json, true)
    override def dbTableName = "project"
}

class ProjectType extends Audited[ProjectType] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[ProjectType] with ActiveInactivable[ProjectType]{ 
    def getSingleton = ProjectType
    override def updateShortName = false
    object obs extends MappedPoliteString(this,255)
    object class_? extends MappedBoolean(this) {
        override def defaultValue = false
        override def dbColumnName = "class"
    }
}

object ProjectType extends ProjectType with LongKeyedMapperPerCompany[ProjectType]  with  OnlyActive[ProjectType]

class ProjectClass extends Audited[ProjectClass] with PerCompany with IdPK 
    with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[ProjectClass] with ActiveInactivable[ProjectClass]
    with Siteble 
    { 
    def getSingleton = ProjectClass
    override def updateShortName = false
    object obs extends MappedPoliteString(this,255)
    object projectType extends  MappedLongForeignKey(this, ProjectType)

    def projectTypeName = projectType.obj match {
        case Full(t) => t.name.is
        case _ => ""
    }

    def imagePath = "Projectclass"

}

object ProjectClass extends ProjectClass with LongKeyedMapperPerCompany[ProjectClass]  with  OnlyActive[ProjectClass]

class StakeHolder extends Audited[StakeHolder] with KeyedMapper[Long, StakeHolder] with BaseLongKeyedMapper
     with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy
     with ActiveInactivable[StakeHolder] with PerUnit with CompanyIdable[StakeHolder] {
    def getSingleton = StakeHolder

    object project extends MappedLong(this)
    object business_pattern extends MappedLongForeignKey(this, Customer)
    object stakeHolderType extends  MappedLongForeignKey(this, StakeHolderType)
    object startAt extends EbMappedDateTime(this) {
        override def defaultValue = new Date()
    }
    object endAt extends EbMappedDateTime(this)
    object obs extends MappedString(this, 4000)
    
    object approved extends MappedString(this, 1) {
        override def defaultValue = "N"; //StakeHolder.Approveds.Undefined;
    }

    def bpName = business_pattern.obj match {
        case Full(t) => t.name.is
        case _ => ""
    }

    def stakeHolderTypeName = stakeHolderType.obj match {
        case Full(t) => t.name.is
        case _ => ""
    }
}

object StakeHolder extends StakeHolder with LongKeyedMapperPerCompany[StakeHolder] with OnlyCurrentCompany[StakeHolder] with OnlyActive[StakeHolder]{
    object Approveds extends Enumeration {
     type Approveds = Value
     val Approved = Value("A")
     val NotApproved = Value("R")
     val Undefined = Value("N")
    }

}

class StakeHolderType extends Audited[StakeHolderType] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[StakeHolderType] with ActiveInactivable[StakeHolderType]{ 
    def getSingleton = StakeHolderType
    override def updateShortName = false
    object obs extends MappedPoliteString(this,255)
    object orderInreport extends MappedInt(this){
        override def defaultValue = 10
    }
}

object StakeHolderType extends StakeHolderType with LongKeyedMapperPerCompany[StakeHolderType]  with  OnlyActive[StakeHolderType]{
    val MANAGER = 2
    val SPONSOR = 1
}

class ProjectStage extends Audited[ProjectStage] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[ProjectStage] with ActiveInactivable[ProjectStage]{ 
    def getSingleton = ProjectStage
    override def updateShortName = false
    object obs extends MappedPoliteString(this,255)
}

object ProjectStage extends ProjectStage with LongKeyedMapperPerCompany[ProjectStage]  with  OnlyActive[ProjectStage]

