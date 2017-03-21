package code
package model 

import net.liftweb._ 
import mapper._ 
import http._ 
import SHtml._ 
import util._ 
import json._

import java.util.Date

class JobRequisition extends Audited[JobRequisition] with KeyedMapper[Long, JobRequisition] with BaseLongKeyedMapper
     with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy with NameSearchble[JobRequisition] 
     with ActiveInactivable[JobRequisition] with PerUnit with CompanyIdable[JobRequisition]
     with Siteble {
    def getSingleton = JobRequisition
    override def updateShortName = false

    object startAt extends EbMappedDateTime(this) {
        override def defaultValue = new Date()
    }
    object endAt extends EbMappedDateTime(this)
    object revalidationDate extends EbMappedDateTime(this)
	object obs extends MappedString(this, 4000)
    object essential extends MappedString(this, 4000)
    object wish extends MappedString(this, 4000)
    object benefits extends MappedString(this, 4000)

    def updateFromJson(json: JsonAST.JObject) = {
        json.values.map((value) =>{
            this.fieldByName(value._1).get.set(value._2)
        })
        this.save
    }
    def imagePath = "jobrequisition"

}

object JobRequisition extends JobRequisition 
        with LongKeyedMapperPerCompany[JobRequisition] 
        with OnlyActive[JobRequisition]
         with SitebleMapper[JobRequisition]{
    
    def createFromJson(json: JsonAST.JObject) = decodeFromJSON_!(json, true)
}

