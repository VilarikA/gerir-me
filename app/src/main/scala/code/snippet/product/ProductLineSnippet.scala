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


class  ProductLineSnippet  extends BootstrapPaginatorSnippet[ProductLine] {
	
	/**
	* Pagination Methods
	*/
	def pageObj = ProductLine

	def findForListParamsWithoutOrder: List[QueryParam[ProductLine]] = List(Like(ProductLine.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"))

	def findForListParams: List[QueryParam[ProductLine]] = List(Like(ProductLine.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"),OrderBy(ProductLine.name, Ascending), StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage))


	def list(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val ac = ProductLine.findByKey(id.toLong).get	
		  				ac.delete_!
		  				S.notice("Linha de produto excluída com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Linha de produto não existe!")
		  				case _ => S.error("Linha de produto não pode ser excluída!")
		  			}
			
			}

			ProductLine.findAllInCompany.flatMap(ac => 
			bind("f", xhtml,"name" -> Text(ac.name.is),
							"obs" -> Text(ac.obs.is),
							"actions" -> <a class="btn" href={"/product_admin/edit_line?id="+ac.id.is}>Editar</a>,
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger","data-confirm-message"->{" excluir a linha de produto "+ac.name.is}),
							"_id" -> SHtml.text(ac.id.is.toString, id = _),
							"id" ->Text(ac.id.is.toString)
				)
			)
	}

	def getLine:ProductLine = {
		def id = S.param("id") openOr "0"
		id match {
			case "0" => ProductLine.create
			case _ => ProductLine.findByKey(id.toLong).get
		}
	}

	def maintain = {
		try{
			var ac:ProductLine = getLine
			def process(): JsCmd= {
				ac.company(AuthUtil.company)
			   	ac.save	
			   	S.notice("Linha de produto salva com sucesso!")
			}
		    "name=name" #> (SHtml.text(ac.name.is, ac.name(_)))&
		    "name=short_name" #> (SHtml.text(ac.short_name.is, ac.short_name(_)))&
			"name=obs" #> (SHtml.textarea(ac.obs.is, ac.obs(_))++SHtml.hidden(process))

		}catch {
		    case e: NoSuchElementException => S.error("Linha não existe!")
		    "#product_form *" #> NodeSeq.Empty
  		}
  	}
}