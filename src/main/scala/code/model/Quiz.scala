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

class Quiz extends Audited[Quiz] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[Quiz] with ActiveInactivable[Quiz]{ 
    def getSingleton = Quiz
    override def updateShortName = false
    object userGroup extends  MappedLongForeignKey(this, UserGroup)
    object obs extends MappedPoliteString(this,255)
    object message extends MappedPoliteString(this,40000)
    object share_? extends MappedBoolean(this) {
        override def defaultValue = true
        override def dbColumnName = "share"
    }
    object showInRecords_? extends MappedBoolean(this) {
        override def defaultValue = false
        override def dbColumnName = "showinrecords"
    }

    def userGroupName = userGroup.obj match {
        case Full(t) => t.short_name.is
        case _ => ""
    }

    def sections = {
        QuizSection.findAll(By(QuizSection.quiz, this.id.is), OrderBy(QuizSection.orderInQuiz, Ascending))
    }

    def quizLabel = if(AuthUtil.? && (AuthUtil.company.appType.isEsmile||AuthUtil.company.appType.isEdoctus||
       AuthUtil.company.appType.isEphysio)){
        "Prontuário"
    } else {
        "Questionário"
    }

    override def delete_! = {
        if(QuizApplying.count(By(QuizApplying.quiz,this.id)) > 0){
            throw new RuntimeException("Existe aplicação deste avaliação!")
        }
        if(QuizSection.count(By(QuizSection.quiz,this.id)) > 0){
            throw new RuntimeException("Existe seção nesta avaliação!")
        }

        super.delete_!
    }

    override def save() = {
        super.save  
        if(QuizSection.count(By(QuizSection.quiz,this.id)) < 1){
            val ac = QuizSection.createInCompany.
            quiz(this.id).
            name(this.name)
            ac.save
        }
        super.save  
    }

}

object Quiz extends Quiz with LongKeyedMapperPerCompany[Quiz]  
    with  OnlyActive[Quiz] {
    def findAllRecords = findAllInCompany()
}
