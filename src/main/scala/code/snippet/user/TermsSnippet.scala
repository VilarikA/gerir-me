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



class  TermsSnippet  extends BootstrapPaginatorSnippet[Terms] {
	/**
	* Pagination Methods
	*/
	def pageObj = Terms

	def findForListParamsWithoutOrder: List[QueryParam[Terms]] = 
	List(Like(Terms.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"))
	override def page = {
		if(!showAll){
			super.page
		}else{
			Terms.findAllInCompanyWithInactive(findForListParams :_*)
		}
	}

	def findForListParams: List[QueryParam[Terms]] = List(Like(Terms.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"),OrderBy(Terms.name, Ascending), 
		StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage))


	def list(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val ac = Terms.findByKey(id.toLong).get	
		  				ac.delete_!
		  				S.notice("Termo excluído com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Termo não existe!")
		  				case _ => S.error("Termo não pode ser excluído!")
		  			}
			
			}

			page.flatMap(ac => 
			bind("f", xhtml,"name" -> Text(ac.name.is),
							"obs" -> Text(ac.obs.is),
							"actions" -> <a alt="Editar registro" href={"/user/terms?id="+ac.id.is}> <img src='/images/edit.png'/></a>,
							"delete" -> SHtml.submit("",delete,"class" -> "delete-button danger","data-confirm-message"->{" excluir o registro "+ac.name}),							
							"_id" -> SHtml.text(ac.id.is.toString, id = _),
							"id" ->Text(ac.id.is.toString)
				)
			)
	}

	def getTerms:Terms = {
		def id = S.param("id") openOr "0"
		id match {
			case "0" => Terms.create
			case _ => Terms.findByKey(id.toLong).get
		}
	}

	def maintain = {
		try{
			var ac:Terms = getTerms
			def process(): JsCmd= {
				try{
					ac.company(AuthUtil.company)
			   		ac.save	
			   		S.notice("Termo salvo com sucesso!")
			   		S.redirectTo("/user/terms?id="+ac.id.is)
			   	}catch{
					case (e:net.liftweb.http.ResponseShortcutException) =>{
						throw e
					}			   		
			   		case e: RuntimeException => S.error(e.getMessage)
			   	}
			}
		    "name=name" #> (SHtml.text(ac.name.is, ac.name(_)))&
		    "name=short_name" #> (SHtml.text(ac.short_name.is, ac.short_name(_)))&
		    "name=message" #> (SHtml.textarea(ac.message.is, ac.message(_)))&
		    "name=status" #> (SHtml.select(status,Full(ac.status.is.toString),(v:String) => ac.status(v.toInt)))&
		    "name=obs" #> (SHtml.text(ac.obs.is, ac.obs(_))++SHtml.hidden(process))
		}catch {
		    case e: NoSuchElementException => S.error("Termo não existe!")
		    "#product_form *" #> NodeSeq.Empty
  		}
  	}
}