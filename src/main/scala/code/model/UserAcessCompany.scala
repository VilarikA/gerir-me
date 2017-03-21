package code
package model 

import net.liftweb._ 
import mapper._ 
import code.util._
import json._
import net.liftweb.widgets.gravatar.Gravatar
import net.liftweb.common._
import net.liftweb.util._


import java.util.Date;

class UserAcessCompany extends LongKeyedMapper[UserAcessCompany] with PerCompany  with IdPK with CreatedUpdated  with CreatedUpdatedBy {
    object targetCompany extends MappedLongForeignKey(this,Company){
        override def dbIndexed_? = true
    }

    object user extends MappedLongForeignKey(this,User){
        override def dbIndexed_? = true
        override def dbColumnName = "business_pattern"
    }

    object allowed_? extends MappedBoolean(this){
        override def dbColumnName = "allowed"
    }

    def getSingleton = UserAcessCompany
}

object UserAcessCompany extends UserAcessCompany with LongKeyedMapperPerCompany[UserAcessCompany] with OnlyCurrentCompany[UserAcessCompany]{
    override def dbTableName = "bpcompany"
}

