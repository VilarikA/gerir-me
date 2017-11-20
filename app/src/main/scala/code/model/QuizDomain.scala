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

class QuizDomain extends Audited[QuizDomain] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[QuizDomain] with ActiveInactivable[QuizDomain]{ 
    def getSingleton = QuizDomain
    override def updateShortName = false
    object obs extends MappedPoliteString(this,255)
    object min extends MappedPoliteString(this,255)
    object max extends MappedPoliteString(this,255)

    override def delete_! = {
        if(QuizDomainItem.count(By(QuizDomainItem.quizDomain,this.id)) > 0){
           throw new RuntimeException("Existe item para este domínio!")
        }

        super.delete_!
    }

}

object QuizDomain extends QuizDomain with LongKeyedMapperPerCompany[QuizDomain]  
    with  OnlyActive[QuizDomain]
