
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
import java.util.{Date, Calendar};

class  TreatmentSnippet extends PaginatorSnippet [Treatment] {

	def getDateAsString(date:Date) = {		
		date match {
			case d:Date => {
				if (d.getHours == 0) {
					getDatePlus3(d)
				} else {
					d.getTime.toString
				}
			}
			case _ => ""
		}				
	}	

	def getDatePlus3(date:Date) = {
		val cal = Calendar.getInstance
		cal.setTime(date)
		cal.add(Calendar.HOUR_OF_DAY, 3) 
		cal.getTime.getTime.toString
	}

	def pageObj = Treatment
	def obs = S.param("obs") match {
		case Full(s) => s
		case _ => ""
	}	

//	def products = ("0", "Selecione um Serviço") :: Activity.findAllInCompany.map(t => (t.id.is.toString,t.name.is))
	def users = ("0", "Selecione um Profissional") :: User.findAllInCompany(OrderBy(User.name, Ascending)).map(t => (t.id.is.toString, t.name.is))
	def units = ("0", "Selecione uma Unidade") :: CompanyUnit.findAllInCompany(OrderBy(CompanyUnit.name, Ascending)).map(t => (t.id.is.toString, t.name.is))
	def icds = ("0" -> "Selecione um cid")::Icd.findAll(By(Icd.section,"S"),OrderBy(Icd.namecomp, Ascending)).map(t => (t.id.is.toString,t.namecomp.is))
	def offsales = ("0" -> "Selecione um convênio")::OffSale.findAllInCompany(OrderBy(OffSale.name, Ascending)).map(t => (t.id.is.toString,t.name.is))
	def hospitalizationtypes = DomainTable.findAll(OrderBy(DomainTable.cod, Ascending),By(DomainTable.domain_name, "tipoInternacao")).map(t => (t.cod.is.toString,t.name.is))
//,By(Icd.section,"S")

//	def findForListParamsWithoutOrder: List[QueryParam[Treatment]] = List(Like(Treatment.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"))
	def findForListParamsWithoutOrder: List[QueryParam[Treatment]] = List(Like(Treatment.obs,"%"+BusinessRulesUtil.clearString(obs)+"%"))
/*	override def page = {
		if(!showAll){
			super.page
		}else{
			//Treatment.findAllInCompanyWithInactive(findForListParams :_*)
			Treatment.findAllInCompany(findForListParams :_*)
		}
	}
*/

/*
	override def page = {
		Treatment.findAllInCompany(findForListParams :_*)
	}
*/
	override lazy val count = {
		AuthUtil.company.count(pageObj,findForListParamsWithoutOrder)
	}
	override def page = AuthUtil.company.pagination(pageObj,findForListParams)


	val today = new (Date);

//	def findForListParams: List[QueryParam[Treatment]] = List(Like(Treatment.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"),OrderBy(Treatment.name, Ascending), StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage))
	def findForListParams: List[QueryParam[Treatment]] = 
		List(By (Treatment.id,1053838),By (Treatment.hasDetail, true), NotBy (Treatment.status,Treatment.TreatmentStatus.Deleted),
		Like(Treatment.obs,"%"+BusinessRulesUtil.clearString(obs)+"%"),OrderBy(Treatment.dateEvent, Descending), StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage))


	def list(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val ac = Treatment.findByKey(id.toLong).get	
		  				ac.delete_!
		  				S.notice("Atendimento excluído com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Atendimento não existe!")
		  				case _ => S.error("Atendimento não pode ser excluído!")
		  			}
			}

			page.flatMap(ac => 
			bind("f", xhtml,"name" -> Text(ac.customerName),
							//"product" -> Text(ac.productName),
							"start" -> Text(Project.dateToStrOrEmpty(ac.start.is)),
							//"valuediscount" -> Text(ac.valueDiscount.is.toString),
							//"valuesession" -> Text(ac.valueSession.is.toString),
							//"numsession" -> Text(ac.numSession.is.toString),
							"actions" -> <a class="btn" href={"/treatment/treatment?id="+ac.id.is}>Editar</a>,
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger","data-confirm-message" -> {" excluir o Atendimento "+ac.obs}),
							"_id" -> SHtml.text(ac.id.is.toString, id = _),
							"id" ->Text(ac.id.is.toString)
				)
			)
	}

	def getTreatment:Treatment = {
		def id = S.param("id") openOr "0"
		id match {
			case "0" => Treatment.create
			case _ => Treatment.findByKey(id.toLong).get
		}
	}


	def maintain = {
		try{
			var ac:Treatment = getTreatment
			def process(): JsCmd= {
				try{
					ac.company(AuthUtil.company)
//					ac.weekDays(S.params("weekDays").foldLeft("")(_+","+_))
					ac.save
				   	S.notice("Atendimento salvo com sucesso!")
				   	S.redirectTo("/treatment/treatment?id="+ac.id.is)
		   		}catch{
					case (e:net.liftweb.http.ResponseShortcutException) =>{
						throw e
					}
					case (e:Exception) => {
						S.error(e.getMessage)
					}
				}
			}
			"name=customer" #> (SHtml.text(ac.customer.is.toString, (p:String) => ac.customer(p.toLong)))&
		    "name=user" #> (SHtml.select(users,Full(ac.user.is.toString),(v:String) => ac.user(v.toLong)))&
			"name=start" #> (SHtml.text(getDateAsString(ac.start.is),
						(date:String) => {
							ac.start(Project.strOnlyDateToDate(date))
						}))&
			"name=end" #> (SHtml.text(getDateAsString(ac.end.is),
						(date:String) => {
							ac.end(Project.strOnlyDateToDate(date))
						}))&
		    "name=unit" #> (SHtml.select(units,Full(ac.unit.is.toString),(v:String) => ac.unit(v.toLong)))&
//			"name=icd" #> (SHtml.select(icds,Full(ac.getTreatEdoctus.icd.is.toString), (s:String) => ac.getTreatEdoctus.icd(s.toLong)))&
			"name=icd" #> (SHtml.text(ac.getTreatEdoctus.icd.is.toString, (p:String) => ac.getTreatEdoctus.icd(p.toLong)))&
			"name=offsale" #> (SHtml.select(offsales,Full(ac.getTreatEdoctus.offsale.is.toString), (s:String) => ac.getTreatEdoctus.offsale(s.toLong)))&
			"name=hospitalizationType" #> (SHtml.select(hospitalizationtypes,Full(ac.getTreatEdoctus.hospitalizationType.is), ac.getTreatEdoctus.hospitalizationType(_)))&
			"name=obs" #> (SHtml.textarea(ac.obs.is, ac.obs(_))++SHtml.hidden(process))
//			"name=status" #> (SHtml.select(status,Full(ac.status.is.toString),(v:String) => ac.status(v))++SHtml.hidden(process))			
		}catch {
		    case e: NoSuchElementException => S.error("Atendimento não existe!")
		    "#Treatment_form *" #> NodeSeq.Empty
  		}
  	}
  	
}

