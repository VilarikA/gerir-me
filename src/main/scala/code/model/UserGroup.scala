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

class UserGroup extends Audited[UserGroup] with PerCompany  with IdPK 
    with CreatedUpdated  with CreatedUpdatedBy with NameSearchble[UserGroup]
    with ActiveInactivable[UserGroup] {
    def getSingleton = UserGroup
    override def updateShortName = false

    object defaultQuiz extends MappedLongForeignKey(this,Quiz)
    object showInCalendar_? extends MappedBoolean(this){
        override def dbColumnName = "showincalendar"
        override def defaultValue = true
    }  

    object obs extends MappedString(this, 4000)
    def defaultQuizName:String = {
        defaultQuiz.obj match {
            case Full(u)=> u.short_name.is
            case _ => ""
        }
    }

    def groupOfDefaultQuiz:Long = {
        defaultQuiz.obj match {
            case Full(u)=> u.userGroup.is
            case _ => 0l
        }
    }

    def groupOfDefaultQuizShared:Boolean = {
        defaultQuiz.obj match {
            case Full(u)=> u.share_?.is
            case _ => false
        }
    }

  override def save() = {
    if (!defaultQuiz.isEmpty) {
        if (this.groupOfDefaultQuiz != this.id.is && !this.groupOfDefaultQuizShared) {
          throw new RuntimeException("Questionário deve ser do grupo ou compartilhado!")
        }
    }
    super.save
  }

}

object UserGroup extends UserGroup with LongKeyedMapperPerCompany[UserGroup] with OnlyCurrentCompany[UserGroup] with OnlyActive[UserGroup]{
    def findAllIncompayForCalendar = findAllInCompany(By(UserGroup.showInCalendar_?, true))
}
