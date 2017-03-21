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

class UserUserGroup extends Audited[UserUserGroup] with PerCompany with IdPK 
    with CreatedUpdated with CreatedUpdatedBy 
    with ActiveInactivable[UserUserGroup] {
    def getSingleton = UserUserGroup
    object user extends MappedLong(this)
    object group extends MappedLongForeignKey(this,UserGroup) 
    object obs extends MappedPoliteString(this,255)
}

object UserUserGroup extends UserUserGroup with LongKeyedMapperPerCompany[UserUserGroup] with OnlyCurrentCompany[UserUserGroup]{
    //override def dbTableName = "bpaccount"
}