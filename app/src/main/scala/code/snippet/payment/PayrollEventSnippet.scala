package code
package snippet

import net.liftweb._
import http._
import code.util._
import code.actors._
import net.liftweb.http.PaginatorSnippet
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

import net.liftweb.json._

import java.util.Date

class  PayrollEventSnippet extends BootstrapPaginatorSnippet[PayrollEvent]{

	implicit val formats = DefaultFormats // Brings in default date formats etc.
	
	def pageObj = PayrollEvent
	
	def findForListParamsWithoutOrder: List[QueryParam[PayrollEvent]] = Like[PayrollEvent](PayrollEvent.search_name,"%"+BusinessRulesUtil.clearString(name)+"%") :: Nil

	def findForListParams: List[QueryParam[PayrollEvent]] = findForListParamsWithoutOrder ::: (List(OrderBy(Customer.name, Ascending), StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage) )).asInstanceOf[List[QueryParam[PayrollEvent]]]

	def list(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val p = PayrollEvent.findByKey(id.toLong).get
		  				p.delete_!
		  				S.notice("Verba de folha excluída com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Verba de folha não existe!")
		  				case _ => S.error("Verba de folha não pode ser excluída!")
		  			}
			}

			page.flatMap(p =>
			bind("f", xhtml,"name" -> Text(p.name.is),
							"actions" -> <a class="btn" href={"/company/payroll_event_form?id="+p.id.is}>Editar</a>,
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger","data-confirm-message" -> {" Excluir a verba "+p.name.is}),
							"_id" -> SHtml.text(p.id.is.toString, id = _),
							"id" ->Text(p.id.is.toString)
				)
			)
	}
	def eventsType = Seq(
	  					PayrollEvent.PROVISION.toString -> "Vencimento" ,
	  					PayrollEvent.DISCOUNT.toString-> "Desconto"
					)
	def getObj = S.param("id") match {
		case Full(s:String) => PayrollEvent.findByKey(s.toInt).get
		case _ => PayrollEvent.create
	}
	
	def maintain = {
		try{
			var p:PayrollEvent = getObj
			def process(): JsCmd= {
				p.company(AuthUtil.company)
			   	p.save	
			   	S.notice("Verba de folha salva com sucesso!")
			}	
		    "name=name" #> (SHtml.text(p.name.is, p.name(_)))&
		    "name=short_name" #> (SHtml.text(p.short_name.is, p.short_name(_)))&
		    "name=isCommition" #> (SHtml.checkbox(p.isCommition_?, p.isCommition_?(_)))&
		    "name=isAdvance" #> (SHtml.checkbox(p.isAdvance_?, p.isAdvance_?(_)))&
		    "name=type" #> (SHtml.select(eventsType,Full(p.eventType.is.toString),(v:String) => p.eventType(v.toInt)))&
			"name=isLiquid" #> (SHtml.checkbox(p.isLiquid_?, p.isLiquid_?(_)))&
			"name=repeat" #> (SHtml.checkbox(p.repeat_?, p.repeat_?(_))++SHtml.hidden(process))
		}catch {
		    case e: NoSuchElementException => S.error("Verba de folha não existe!")
		    "#activity_form *" #> NodeSeq.Empty
  		}
  	}  	
}