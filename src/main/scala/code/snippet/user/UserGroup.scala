package code
package snippet

import net.liftweb._
import http._

import code.util._
import code.actors._
import code.service._
import model._
import http.js._
import JE._
import JsCmds._
import net.liftweb.util._
import net.liftweb.common._
import Helpers._
import scala.xml.{ NodeSeq, Text }
import net.liftweb.mapper._
import net.liftweb.mapper.{StartAt, MaxRows}

object  UserGroupSnippet extends BootstrapPaginatorSnippet[UserGroup] {
	/**
	Pagination Methods
	*/
	def pageObj = UserGroup

	def quizs = ("0", "Selecione um " + Quiz.quizLabel) :: Quiz.
		findAllInCompany(OrderBy(Quiz.name, Ascending)).map(t => (t.id.is.toString,t.name.is))

	def findForListParamsWithoutOrder: List[QueryParam[UserGroup]] = 
	List(Like(UserGroup.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"))
	override def page = {
		if(!showAll){
			super.page
		}else{
			UserGroup.findAllInCompanyWithInactive(findForListParams :_*)
		}
	}

	def findForListParams: List[QueryParam[UserGroup]] = List(Like(UserGroup.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"),OrderBy(UserGroup.name, Ascending), 
		StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage))

	def list(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val ac = UserGroup.findByKey(id.toLong).get	
		  				ac.delete_!
		  				S.notice("Grupo de profissionais excluído com sucesso!");
		  			}catch{
		  				case e: NoSuchElementException => S.error("Grupo de profissionais não existe!")
		  				case _ => S.error("Grupo de profissionais não pode ser excluído!")
		  			}
			
			}

			page.flatMap(ac => 
			bind("f", xhtml,"name" -> Text(ac.name.is),
							"obs" -> Text(ac.obs.is),
							"showincalendar" -> Text(if(ac.showInCalendar_?.is){ "Sim" }else{ "Não" }),
							"defaultquizname" -> Text(ac.defaultQuizName),
							"defaultQuiz" -> Text(ac.defaultQuizName),
							"actions" -> {<span><a class="btn" href={"/group/edit?id="+ac.id.is}>Editar</a></span>},
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger","data-confirm-message" -> {" excluir o Unidade "+ac.name.is}),
							"_id" -> SHtml.text(ac.id.is.toString, id = _),
							"id" ->Text(ac.id.is.toString)
				)
			)
	}

	def maintain() = {
		try{
			var ac:UserGroup = getGroup
			def process(): JsCmd= {
				try {
					ac.company(AuthUtil.company)
				   	ac.save	
				   	S.notice("Grupo de profissionais salvo com sucesso!")
				   	S.redirectTo("/group/edit?id="+ac.id.is)
				}catch{
					case e:RuntimeException => S.error(e.getMessage)
					case _ => S.error("Erro desconhecido tente novamente")
				}
			}
			"name=showInCalendar" #> (SHtml.checkbox(ac.showInCalendar_?, ac.showInCalendar_?(_)))&
		    "name=defaultquiz" #> (SHtml.select(quizs,Full(ac.defaultQuiz.is.toString),(s:String) => ac.defaultQuiz( s.toLong)))&
			"name=status" #> (SHtml.select(status,Full(ac.status.is.toString),(v:String) => ac.status(v.toInt)))&
			"name=obs" #> (SHtml.textarea(ac.obs.is, ac.obs(_)))&
		    "name=short_name" #> (SHtml.text(ac.short_name.is, ac.short_name(_)))&
		    "name=name" #> (SHtml.text(ac.name.is, ac.name(_))++SHtml.hidden(process))
		}catch {
		    case e: NoSuchElementException => S.error("Grupo de profissionais não existe!")
		    "#user_form *" #> NodeSeq.Empty
  		}
  	}
  	def getGroup:UserGroup = {
		var id = S.param("id") openOr "0"
		var ac:UserGroup = {
					id match {
						case "0" => UserGroup.create
						case _ => UserGroup.findByKey(id.toLong).get
					}
		}
		ac
  	}
}