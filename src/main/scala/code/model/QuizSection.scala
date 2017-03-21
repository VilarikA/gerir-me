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
import _root_.java.math.MathContext; 

import java.util.Date

class QuizSection extends Audited[QuizSection] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[QuizSection] with ActiveInactivable[QuizSection]{ 
    def getSingleton = QuizSection
    override def updateShortName = false
    object quiz extends  MappedLongForeignKey(this, Quiz)
    object obs extends MappedPoliteString(this,255)
    object orderInQuiz extends MappedInt(this){
	    override def defaultValue = 10
	}
    object weight extends MappedDecimal(this,MathContext.DECIMAL64,4)
    object rank extends MappedDecimal(this,MathContext.DECIMAL64,4)
    object rankPercent extends MappedDecimal(this,MathContext.DECIMAL64,4)

    def quizName = quiz.obj match {
        case Full(t) => t.short_name.is
        case _ => ""
    }

    def questions = {
        QuizQuestion.findAll(By(QuizQuestion.quizSection, this.id.is), OrderBy(QuizQuestion.orderInSection, Ascending))
    }

    override def delete_! = {
        if(QuizQuestion.count(By(QuizQuestion.quizSection,this.id)) > 0){
            throw new RuntimeException("Existe questão para esta seção!")
        }

        super.delete_!
    }

}

object QuizSection extends QuizSection with LongKeyedMapperPerCompany[QuizSection]  
    with  OnlyActive[QuizSection]
