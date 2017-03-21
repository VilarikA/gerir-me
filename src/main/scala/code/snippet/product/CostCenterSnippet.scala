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


class  CostCenterSnippet  extends BootstrapPaginatorSnippet[CostCenter] {
	val parentReg = ("0" ,"Nenhum") :: CostCenter.findAllInCompany(By(CostCenter.parent_?,true)).map(a =>(a.id.is.toString,a.name.is))
	/**
	* Pagination Methods
	*/
	def pageObj = CostCenter

	def findForListParamsWithoutOrder: List[QueryParam[CostCenter]] = List(Like(CostCenter.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"))
	override def page = {
		if(!showAll){
			super.page
		}else{
			CostCenter.findAllInCompanyWithInactive(findForListParams :_*)
		}
	}

	def findForListParams: List[QueryParam[CostCenter]] = List(Like(CostCenter.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"),OrderBy(CostCenter.name, Ascending), StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage))


	def list(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val cc = CostCenter.findByKey(id.toLong).get	
		  				cc.delete_!
		  				S.notice("Centro de custo excluído com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Centro de custo não existe!")
		  				case _ => S.error("Centro de custo não pode ser excluído!")
		  			}
			
			}

			page.flatMap(cc => 
			bind("f", xhtml,"name" -> Text(cc.name.is),
							"order" -> Text(cc.orderInReport.is.toString),
							"parentReg" -> Text(
														cc.parentReg.obj match {
															case Full(p) => p.name.is
															case _ => ""
														}
														
												),
							"isparent" -> Text(if(cc.parent_?.is){ "Sim" }else{ "Não" }),											
							"actions" -> <a class="btn" href={"/financial_admin/cost_center?id="+cc.id.is}>Editar</a>,
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger","data-confirm-message"->{" excluir o centro de custo "+cc.name.is}),
							"_id" -> SHtml.text(cc.id.is.toString, id = _),
							"id" ->Text(cc.id.is.toString)
				)
			)
	}

	def getCostCenter:CostCenter = {
		def id = S.param("id") openOr "0"
		id match {
			case "0" => CostCenter.create
			case _ => CostCenter.findByKey(id.toLong).get
		}
	}

	def maintain = {
		try{
			var cc:CostCenter = getCostCenter
			def process(): JsCmd= {
				try{
					cc.company(AuthUtil.company)
				
			   		cc.save	
			   		S.notice("Centro de custo salvo com sucesso!")
			   		S.redirectTo("/financial_admin/cost_center?id="+cc.id.is)
			   	}catch{
					case (e:net.liftweb.http.ResponseShortcutException) =>{
						throw e
					}			   		
			   		case e: RuntimeException => S.error(e.getMessage)
			   	}
			}
		    "name=name" #> (SHtml.text(cc.name.is, cc.name(_)))&
		    "name=short_name" #> (SHtml.text(cc.short_name.is, cc.short_name(_)))&
		    "name=obs" #> (SHtml.text(cc.obs.is, cc.obs(_)))&
		    "name=order" #> (SHtml.text(cc.orderInReport.is.toString, (s:String) => cc.orderInReport(s.toInt)))&
		    "name=parent_reg" #> (SHtml.select(parentReg,Full(cc.parentReg.is.toString),(s:String) => cc.parentReg(s.toLong)))&
		    "name=is_parent" #> (SHtml.checkbox(cc.parent_?.is,cc.parent_?(_)))&
		    "name=status" #> (SHtml.select(status,Full(cc.status.is.toString),(v:String) => cc.status(v.toInt))++SHtml.hidden(process))
		}catch {
		    case e: NoSuchElementException => S.error("Centro de custo não existe!")
		    "#product_form *" #> NodeSeq.Empty
  		}
  	}
}