
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

class  JobRequisitionSnippet extends BootstrapPaginatorSnippet[JobRequisition] {

	def pageObj = JobRequisition

	def units = ("0", "Nenhuma Unidade") :: CompanyUnit.findAllInCompany().map(t => (t.id.is.toString, t.name.is))

	def findForListParamsWithoutOrder: List[QueryParam[JobRequisition]] = List(Like(JobRequisition.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"))
	override def page = {
		if(!showAll){
			super.page
		}else{
			JobRequisition.findAllInCompanyWithInactive(findForListParams :_*)
		}
	}

	def findForListParams: List[QueryParam[JobRequisition]] = List(Like(JobRequisition.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"),OrderBy(JobRequisition.name, Ascending), StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage))


	def list(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val ac = JobRequisition.findByKey(id.toLong).get	
		  				ac.delete_!
		  				S.notice("Vaga excluída com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Vaga não existe!")
		  				case _ => S.error("Vaga não pode ser excluída!")
		  			}
			
			}

			page.flatMap(ac => 
			bind("f", xhtml,"name" -> Text(ac.name.is),
							"actions" -> <a class="btn" href={"/user/jobrequisition?id="+ac.id.is}>Editar</a>,
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger","data-confirm-message" -> {" excluir o serviço "+ac.name}),
							"_id" -> SHtml.text(ac.id.is.toString, id = _),
							"id" ->Text(ac.id.is.toString)
				)
			)
	}

	def getJobRequisition:JobRequisition = {
		def id = S.param("id") openOr "0"
		id match {
			case "0" => JobRequisition.create
			case _ => JobRequisition.findByKey(id.toLong).get
		}
	}
	

	def maintain = {
		try{
			var ac:JobRequisition = getJobRequisition
			def process(): JsCmd= {
				ac.company(AuthUtil.company)
				ac.save
			   	S.notice("Vaga salva com sucesso!")
			}
			"name=allowshowonsite" #> (SHtml.checkbox(ac.allowShowOnSite_?, ac.allowShowOnSite_?(_)))&
			"name=allowshowonportal" #> (SHtml.checkbox(ac.allowShowOnPortal_?, ac.allowShowOnPortal_?(_)))&
			"name=moderatedportal" #> (SHtml.checkbox(ac.moderatedPortal_?, ac.moderatedPortal_?(_)))&
		    "name=sitetitle" #> (SHtml.text(ac.siteTitle.is, ac.siteTitle(_)))&
		    "name=sitedescription" #> (SHtml.textarea(ac.siteDescription.is, ac.siteDescription(_)))&
		    "name=name" #> (SHtml.text(ac.name.is, ac.name(_)))&
		    "name=short_name" #> (SHtml.text(ac.short_name.is, ac.short_name(_)))&
			"name=startat" #> (SHtml.text(getDateAsString(ac.startAt.is),
						(date:String) => {
							ac.startAt(Project.strOnlyDateToDate(date))
						}))&
			"name=endat" #> (SHtml.text(getDateAsString(ac.endAt.is),
						(date:String) => {
							ac.endAt(Project.strOnlyDateToDate(date))
						}))&
			"name=obs" #> (SHtml.textarea(ac.obs.is, ac.obs(_)))&
			"name=essential" #> (SHtml.textarea(ac.essential.is, ac.essential(_)))&
			"name=wish" #> (SHtml.textarea(ac.wish.is, ac.wish(_)))&
			"name=benefits" #> (SHtml.textarea(ac.benefits.is, ac.benefits(_)))&
		    "name=unit" #> (SHtml.select(units,Full(ac.unit.is.toString),(v:String) => ac.unit(v.toLong)))&
			"name=status" #> (SHtml.select(status,Full(ac.status.is.toString),(v:String) => ac.status(v.toInt))++SHtml.hidden(process))			
		}catch {
		    case e: NoSuchElementException => S.error("Vaga não existe!")
		    "#jobrequisition_form *" #> NodeSeq.Empty
  		}
  	}
  	
}
