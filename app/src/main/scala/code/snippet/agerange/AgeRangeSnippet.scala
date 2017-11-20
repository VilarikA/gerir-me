
package code
package snippet

import net.liftweb._
import http._
import code.util._
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

class  AgeRangeSnippet extends BootstrapPaginatorSnippet[AgeRange] {

	def pageObj = AgeRange

	def findForListParamsWithoutOrder: List[QueryParam[AgeRange]] = 
	List(Like(AgeRange.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"))
	override def page = {
		if(!showAll){
			super.page
		}else{
			AgeRange.findAllInCompanyWithInactive(findForListParams :_*)
		}
	}

	def findForListParams: List[QueryParam[AgeRange]] = List(Like(AgeRange.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"),OrderBy(AgeRange.name, Ascending), 
		StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage))

	def list(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val ac = AgeRange.findByKey(id.toLong).get	
		  				ac.delete_!
		  				S.notice("Faixa etária excluída com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Faixa etária não existe!")
		  				case _ => S.error("Faixa etária não pode ser excluída!")
		  			}
			}

			page.flatMap(ac => 
			bind("f", xhtml,"name" -> Text(ac.name.is),
							"obs" -> Text(ac.obs.is),
							"actions" -> <a class="btn" href={"/agerange/agerange?id="+ac.id.is}>Editar</a>,
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger","data-confirm-message" -> {" excluir a Faixa etária "+ac.name}),
							"_id" -> SHtml.text(ac.id.is.toString, id = _),
							"id" ->Text(ac.id.is.toString)
				)
			)
	}

	def getAgeRange:AgeRange = {
		def id = S.param("id") openOr "0"
		id match {
			case "0" => AgeRange.create
			case _ => AgeRange.findByKey(id.toLong).get
		}
	}

	def maintain = {
		try{
			var ac:AgeRange = getAgeRange
			def process(): JsCmd= {
				ac.company(AuthUtil.company)
				ac.save
			   	S.notice("Faixa etária salva com sucesso!")
			   	S.redirectTo("/agerange/agerange?id="+ac.id.is)
			}
		    "name=name" #> (SHtml.text(ac.name.is, ac.name(_)))&
		    "name=short_name" #> (SHtml.text(ac.short_name.is, ac.short_name(_)))&
			"name=obs" #> (SHtml.textarea(ac.obs.is, ac.obs(_)))&
			"name=status" #> (SHtml.select(status,Full(ac.status.is.toString),(v:String) => ac.status(v.toInt))++SHtml.hidden(process))			
		}catch {
		    case e: NoSuchElementException => S.error("Faixa etária não existe!")
		    "#AgeRange_form *" #> NodeSeq.Empty
  		}
  	}
}

