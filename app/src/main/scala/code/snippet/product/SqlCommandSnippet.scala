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


class  SqlCommandSnippet  extends BootstrapPaginatorSnippet[SqlCommand] {
	/**
	* Pagination Methods
	*/
	def pageObj = SqlCommand

	def findForListParamsWithoutOrder: List[QueryParam[SqlCommand]] = List(Like(SqlCommand.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"))
	override def page = {
		if(!showAll){
//			super.page
			SqlCommand.findAllInCompanyOrDefaultCompany(findForListParams :_*)
		}else{
			SqlCommand.findAllInCompanyWithInactive(findForListParams :_*)
		}
	}

	def findForListParams: List[QueryParam[SqlCommand]] = List(Like(SqlCommand.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"),OrderBy(SqlCommand.name, Ascending), StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage))


	def list(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val cc = SqlCommand.findByKey(id.toLong).get	
						if (cc.company != AuthUtil.company) {
							throw new RuntimeException("Você não pode excluir este comando sql!")
						}
		  				cc.delete_!
		  				S.notice("Sql excluído com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Sql não existe!")
				   		case e: RuntimeException => S.error(e.getMessage)
		  				case _ => S.error("Sql não pode ser excluído!")
		  			}
			
			}

			page.flatMap(cc => 
			bind("f", xhtml,"name" -> Text(cc.name.is),
							"obs" -> Text(cc.obs.is),
							"actionsedt" -> <a class="btn" href={"/company_log/sql_command?id="+cc.id.is}>Editar</a>,
							"actions" -> <a class="btn success" href={"/company_log/sql_run?id="+cc.id.is}>Executar</a>,
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger","data-confirm-message"->{" excluir o Sql "+cc.name.is}),
							"_id" -> SHtml.text(cc.id.is.toString, id = _),
							"id" ->Text(cc.id.is.toString)
				)
			)
	}

	def getSqlCommand:SqlCommand = {
		def id = S.param("id") openOr "0"
		id match {
			case "0" => SqlCommand.create
			case _ => SqlCommand.findByKey(id.toLong).get
		}
	}

	def maintain = {
		try{
			var cc:SqlCommand = getSqlCommand
			def process(): JsCmd= {
				try{
					if (cc.company != AuthUtil.company) {
						throw new RuntimeException("Você não pode salvar este comando sql! copie e salve um novo na sua empresa.")
					}
					// isso com o percompany não precisa mais
					// cc.company(AuthUtil.company)
				
			   		cc.save	
			   		S.notice("Registro salvo com sucesso!")
			   		S.redirectTo("/company_log/sql_command?id="+cc.id.is)
			   	}catch{
					case (e:net.liftweb.http.ResponseShortcutException) =>{
						throw e
					}			   		
			   		case e: RuntimeException => S.error(e.getMessage)
			   	}
			}
		    "name=name" #> (SHtml.text(cc.name.is, cc.name(_)))&
		    "name=sqlcmd" #> (SHtml.textarea(cc.sqlcmd.is, cc.sqlcmd(_)))&
		    "name=status" #> (SHtml.select(status,Full(cc.status.is.toString),(v:String) => cc.status(v.toInt)))&
		    "name=obs" #> (SHtml.textarea(cc.obs.is, cc.obs(_))++SHtml.hidden(process))
		}catch {
		    case e: NoSuchElementException => S.error("Sql não existe!")
		    "#product_form *" #> NodeSeq.Empty
  		}
  	}
}
