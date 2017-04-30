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

class QuizQuestion extends Audited[QuizQuestion] with PerCompany with IdPK with CreatedUpdated with CreatedUpdatedBy 
    with NameSearchble[QuizQuestion] with ActiveInactivable[QuizQuestion]{ 
    def getSingleton = QuizQuestion
    override def updateShortName = false
    object quizSection extends  MappedLongForeignKey(this, QuizSection)
    object obs extends MappedPoliteString(this,255)
    object orderInSection extends MappedInt(this){
	    override def defaultValue = 10
	}
    object history_? extends MappedBoolean(this){
        override def defaultValue = false
        override def dbColumnName = "history"
    }    
    object rank extends MappedDecimal(this,MathContext.DECIMAL64,4)
    object quizDomain extends MappedLongForeignKey(this, QuizDomain) // opcional
    object quizDomainItemRight extends MappedLongForeignKey(this, QuizDomainItem) // opcional
//    object quizQuestionTypeStr extends MappedPoliteString(this,5)
    object quizQuestionFormat extends MappedInt(this)with LifecycleCallbacks { // em função do número de dias
        override def defaultValue = 0 // questões curtas - label
        // 1 questoes longas - paragrafo
    }
    object quizQuestionType extends MappedInt(this)with LifecycleCallbacks { // em função do número de dias
  /*    override def beforeSave() {
          super.beforeSave;
          println ("vaiii ========== " + quizQuestionTypeStr)
          if (quizQuestionTypeStr == "") {
            // nada 
          } else {
            var straux : String = quizQuestionTypeStr;
          println ("vaiii ========== to int " + straux.toInt)
            this.set(straux.toInt)
          }
      } 
*/
    }
    //object quizQuestionType extends MappedEnum(this,QuizQuestion.QuizQuestionType){
        /*
        0 Texto
        1 Texto longo
        2 Escolha de uma lista (domínio)
        3 Múltipla escolha (domínio)
        4 Escolha de uma lista (+ de uma opção)
        */


    def quizSectionName = quizSection.obj match {
        case Full(t) => t.short_name.is
        case _ => ""
    }

    def domain: List[QuizDomainItem] = quizDomain.obj match {
        case Full(d) => QuizDomainItem.findAll(By(QuizDomainItem.quizDomain, quizDomain.is), OrderBy(QuizDomainItem.orderInDomain, Ascending))
        case _ => Nil
    }

    override def delete_! = {
        if(QuizAnswer.count(By(QuizAnswer.quizQuestion,this.id)) > 0){
           throw new RuntimeException("Existe resposta para esta questão!")
        }
        super.delete_!
    }

}

object QuizQuestion extends QuizQuestion with LongKeyedMapperPerCompany[QuizQuestion]  
    with  OnlyActive[QuizQuestion] {
    object QuizQuestionType extends Enumeration {
        type QuizQuestionType = Value
        val text, paragraph, choosefromlist, multiplechoice, severaloptions = Value
    }
/*
    val types = Seq(
                QuizQuestionType.text.toString -> "Texto",
                QuizQuestionType.paragraph.toString -> "Parágrafo",
                QuizQuestionType.choosefromlist.toString -> "Escolha de uma lista"
    )
*/
}
