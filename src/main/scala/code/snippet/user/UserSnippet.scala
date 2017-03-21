
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

class  UserSnippet extends BootstrapPaginatorSnippet[User] {

	/**	
	Pagination Methods
	*/
	def pageObj = User
	
	def statusFilter: List[Int] = if(showAll){
		List(User.STATUS_OK, User.STATUS_BLOCKED, User.STATUS_INACTIVE)
	}else{
		List(User.STATUS_OK)
	}

	def findForListParamsWithoutOrder: List[QueryParam[User]] = List(Like(User.search_name,"%"+BusinessRulesUtil.clearString(name)+"%")
		, 
		Like(User.mobilePhone,"%"+phone+"%")
		)

	def findForListParams: List[QueryParam[User]] = List(OrderBy(User.search_name, Ascending), Like(User.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"), 
		//Like(User.mobilePhone,"%"+phone+"%"), 
		BySql ("((phone like '%"+phone+"%') or (mobile_phone like '%"+phone+"%') or (email_alternative like '%"+phone+"%'))",IHaveValidatedThisSQL("","")),
		BySql (userGroupList,IHaveValidatedThisSQL("","")),
		StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage), ByList(User.userStatus,statusFilter))

	val days = Seq(
	  				WeekDay.Mon.toString -> "Segunda" ,
	  				WeekDay.Tue.toString -> "Terça" ,
	  				WeekDay.Wed.toString -> "Quarta" ,	
	  				WeekDay.Thu.toString -> "Quinta",
	  				WeekDay.Fri.toString -> "Sexta",
	  				WeekDay.Sat.toString -> "Sábado",
	  				WeekDay.Sun.toString -> "Domingo",
	  				WeekDay.WorkDays.toString -> "Dias de Semana",
	  				WeekDay.All.toString -> "Todos"
  				)

	def honorifictitles = ("0" -> "Selecione uma forma de tratamento")::HonorificTitle.findAll(OrderBy(HonorificTitle.name, Ascending)).map(t => (t.id.is.toString,t.name.is))

	def councils = ("0" -> "Selecione um conselho")::Council.findAll(OrderBy(Council.name, Ascending)).map(t => (t.id.is.toString,t.name.is))

	def degrees = ("0" -> "Selecione um grau de instrução")::InstructionDegree.findAll(OrderBy(InstructionDegree.name, Ascending)).map(t => (t.id.is.toString,t.name.is))

	def units = ("0", "Selecione uma Unidade") :: CompanyUnit.findAllInCompany(OrderBy(CompanyUnit.name, Ascending)).map(t => (t.id.is.toString, t.name.is))
	def groups = ("0", "Selecione um Grupo") :: UserGroup.findAllInCompany(OrderBy(UserGroup.name, Ascending)).map(t => (t.id.is.toString, t.name.is))

	def civilstatuses = ("0" -> "Não Informado")::CivilStatus.findAll(OrderBy(CivilStatus.name, Ascending)).map(t => (t.id.is.toString,t.name.is))

	def phone = S.param("phone") match {
		case Full(s) => s
		case _ => ""
	}

	def userGroup = S.param("userGroup") match {
		case Full(s) => s
		case _ => ""
	}	
//	LogActor ! "PARAMETRo " + userGroup
//	LogActor ! "PARAMETRo LISTA " + userGroupList
    val userGroupList = if(userGroup != ""){
        "group_c = %s ".format (userGroup)
    }else{
        " 1 = 1 "
    }


	def users(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val user = User.findByKey(id.toLong).get	
		  				user.delete_!
		  				S.notice("Profissional excluído com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Profissional não existe!")
		  				case _ => S.error("Profissional não pode ser excluído! Você pode alterar o status para inativo.")
		  			}
			
			}

			page.flatMap(user => 
			bind("f", xhtml,"name" -> Text(user.name.is),
							"short_name" -> Text(user.short_name.is),
							"password" -> Text(user.password.is),
							"email" -> Text(user.email.is),
							"phone" -> Text(user.mobilePhone.is + " " + user.phone.is + " " + user.email_alternative.is),
							"usergroup" -> Text(user.userGroupName),
							"unit" -> Text(user.unitName),
//							"username" -> Text(user.userName.is),
						    //"name=userName" #> (SHtml.text(user.userName.is, user.userName(_))) &
							//"groupPermission" -> SHtml.text(user.groupPermission, (a:String) => {}),
							"gp" -> Text(user.groupPermission.is),
							"actions" -> <a class="btn" href={"/user/edit?id="+user.id.is}>Editar</a>,
//							"actions" -> <a class="btn" href={"/user/edit_new?id="+user.id.is}>Editar</a>,
							"thumb" -> user.thumb ("36"),
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger","data-confirm-message" -> {" excluir o profissional "+user.name.is}),
							"_id" -> SHtml.text(user.id.is.toString, id = _),
							"id" ->Text(user.id.is.toString)
				)
			)
	}
	def workHouers(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val day = WorkHouer.findByKey(id.toLong).get	
		  				day.delete_!
		  				S.notice("Profissional excluído com sucesso!")
		  			}catch{

		  				case e: NoSuchElementException =>{LogActor ! "Dia {"+id+"} não existe!";  S.error("Dia não existe!");}
		  			}
			
			}

			getUser.workHouers.flatMap(workHouer => 
			bind("f", xhtml,"start" -> Text(workHouer.start.is),
							"end" -> Text(workHouer.end.is),
							"start_lanch" -> Text(workHouer.startLanch.is),
							"end_lanch" -> Text(workHouer.endLanch.is),
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger"),
							"day" -> Text(workHouer.day.translate),
							"_id" -> SHtml.text(workHouer.id.is.toString, id = _)
				)
			)
	}	

	def maintain() = {
		try{
			var user:User = getUser
			def process(): JsCmd= {
				try{
					user.company(AuthUtil.company)
					user.groupPermission(S.params("groupPermission").foldLeft("")(_+","+_))
				   	user.save
				   	S.notice("Profissional salvo com sucesso!")
				   	S.redirectTo("/user/edit?id="+user.id.is)
				}catch{
					case (e:net.liftweb.http.ResponseShortcutException) =>{
						throw e
					}
					case (e:Exception) => {
						S.error(e.getMessage)
					}
				}
			}
			"name=allowshowonsite" #> (SHtml.checkbox(user.allowShowOnSite_?, user.allowShowOnSite_?(_)))&
			"name=allowshowonportal" #> (SHtml.checkbox(user.allowShowOnPortal_?, user.allowShowOnPortal_?(_)))&
			"name=moderatedportal" #> (SHtml.checkbox(user.moderatedPortal_?, user.moderatedPortal_?(_)))&
		    "name=sitetitle" #> (SHtml.text(user.siteTitle.is, user.siteTitle(_)))&
		    "name=sitedescription" #> (SHtml.textarea(user.siteDescription.is, user.siteDescription(_)))&
		    "name=external_id" #> (SHtml.text(user.external_id.is, user.external_id(_)))&
			"#img_user" #> user.thumb("192")&
		    "name=name" #> (SHtml.text(user.name.is, user.name(_)))&
		    "name=short_name" #> (SHtml.text(user.short_name.is, user.short_name(_)))&
		    "name=obs" #> (SHtml.textarea(user.obs.is, user.obs(_)))&
//		    "name=userName" #> (SHtml.text(user.userName.is, user.userName(_))) &
		    "name=password" #> (SHtml.password(user.password.is, user.password(_))) &
		    "name=phone" #> (SHtml.text(user.phone.is, user.phone(_)))&
			"name=mobilePhone" #> (SHtml.text(user.mobilePhone.is, user.mobilePhone(_)))&
			"name=email_alternative" #> (SHtml.text(user.email_alternative.is, user.email_alternative(_)))&
			"name=birthday" #> (SHtml.text(getDateAsString(user.birthday),
						(date:String) => {
							user.birthday(Project.strOnlyDateToDate(date))
						}))&			
			"name=hireDate" #> (SHtml.text(getDateAsString(user.hireDate),
						(date:String) => {
							user.hireDate(Project.strOnlyDateToDate(date))
						}))&			
			"name=resignationDate" #> (SHtml.text(getDateAsString(user.resignationDate),
						(date:String) => {
							user.resignationDate(Project.strOnlyDateToDate(date))
						}))&			
		    "name=sex" #> (SHtml.select(sexs,Full(user.sex.is),user.sex(_)))&					    
		    "name=civilstatus" #> (SHtml.select(civilstatuses,Full(user.civilstatus.is.toString),(v:String) => user.civilstatus(v.toInt)))&
		    "name=unit" #> (SHtml.select(units,Full(user.unit.is.toString),(v:String) => user.unit(v.toLong)))&
			"name=document" #> (SHtml.text(user.document.is, user.document(_)))&
			"name=document_identity" #> (SHtml.text(user.document_identity.is, user.document_identity(_)))&
			"name=document_company" #> (SHtml.text(user.document_company.is, user.document_company(_)))&
			"name=document_council" #> (SHtml.text(user.document_council.is, user.document_council(_)))&
		    "name=council" #> (SHtml.select(councils,Full(user.council.is.toString),(s:String) => user.council( s.toLong)))&
		    "name=instructiondegree" #> (SHtml.select(degrees,Full(user.instructiondegree.is.toString),(s:String) => user.instructiondegree( s.toLong)))&
		    "name=state_ref" #> (SHtml.text(user.stateRef.is.toString, (s:String) => user.stateRef(s.toLong)))&
			"name=city_ref" #> (SHtml.text(user.cityRef.is.toString, (s:String) => user.cityRef(s.toLong)))&
			"name=city" #> (SHtml.text(user.city.is, user.city(_)))&
			"name=state" #> (SHtml.text(user.state.is, user.state(_)))&
			"name=street" #> (SHtml.text(user.street.is, user.street(_))) &
			"name=district" #> (SHtml.text(user.district.is, user.district(_)))&
			"name=postal_code" #> (SHtml.text(user.postal_code.is, user.postal_code(_)))&		    
		    "name=pointofreference" #> (SHtml.textarea(user.pointofreference.is, user.pointofreference(_))) &
			"name=lng" #> (SHtml.text(user.lng.is, user.lng(_)))&
			"name=lat" #> (SHtml.text(user.lat.is, user.lat(_)))&
		    "name=group" #> (SHtml.select(groups,Full(user.group.is.toString),(v:String) => user.group(v.toLong)))&
		    "name=status" #> (SHtml.select(status,Full(user.userStatus.is.toString),(v:String) => user.userStatus(v.toInt)))&
			"name=number" #> (SHtml.text(user.number.is, user.number(_)))&		    
			"name=complement" #> (SHtml.text(user.complement.is, user.complement(_)))&
			"name=showInCalendar" #> (SHtml.checkbox(user.showInCalendar_?, user.showInCalendar_?(_)))&
			"name=is_auxiliar" #> (SHtml.checkbox(user.is_auxiliar_?, user.is_auxiliar_?(_)))&
			"name=groupPermission_text" #> (SHtml.text(user.groupPermission, (a:String) => {}))&
			"#img_thumb" #> user.thumb&
			"name=orderInCalendar" #> (SHtml.text(user.orderInCalendar.is.toString, (s:String) => user.orderInCalendar(s.toInt))) &
			"name=parent" #> (SHtml.text(user.parent.is.toString, (p:String) => { 
					if(p != "")
						user.parent(p.toLong)
					else
						user.parent(0)

			}))&
			"name=parent_percent" #> (SHtml.text(user.parent_percent.is.toString, (v:String) =>{ if(v !=""){user.parent_percent(v.toDouble)};}))&
			"name=cancreatecalendarevents" #> (SHtml.checkbox(user.canCreateCalendarEvents_?, user.canCreateCalendarEvents_?(_)))&
			"name=candeletecalendarevents" #> (SHtml.checkbox(user.canDeleteCalendarEvents_?, user.canDeleteCalendarEvents_?(_)))&
			"name=canmovecalendarevents" #> (SHtml.checkbox(user.canMoveCalendarEvents_?, user.canMoveCalendarEvents_?(_)))&
			"name=caneditcalendarevents" #> (SHtml.checkbox(user.canEditCalendarEvents_?, user.canEditCalendarEvents_?(_)))&
			"name=email" #> (SHtml.text(user.email.is, user.email(_))++SHtml.hidden(process))
		}catch {
		    case e: NoSuchElementException => S.error("Profissional não existe!")
		    "#user_form *" #> NodeSeq.Empty
  		}
  	}
  	def getUser:User = {
		var id = S.param("id") openOr "0"
		var user:User = {
					id match {
						case "0" => User.create
						case _ => User.findByKey(id.toLong).get
					}
		}
		user
  	}
  	def workHouersMaintain() = {
  		try{
		  	var day:WorkHouer = WorkHouer.createInCompany
		  	day.user(getUser)
		  	def saveDay(): JsCmd= {
		  		day.save
		  		S.redirectTo("/user/edit?id="+day.user)
		  		S.notice("Adicionado com sucesso!")
			}
			"name=start" #> (SHtml.text(day.start.is, day.start(_)))&
			"name=end" #> (SHtml.text(day.end.is, day.end(_)))&
			"name=start_lanch" #> (SHtml.text(day.startLanch.is, day.startLanch(_)))&
			"name=end_lanch" #> (SHtml.text(day.endLanch.is, day.endLanch(_)))&
			"name=days" #> (SHtml.select(days, Empty,day.day(_))++SHtml.hidden(saveDay))
		}catch {
		    case e: NoSuchElementException => S.error("Profissional não existe!")
		    "#user_form *" #> NodeSeq.Empty
  		}

  	}


  	def usersJsonFromCalendar(html:NodeSeq):NodeSeq = {
  			def unit = S.param("unit") openOr ""
  			def userId = S.param("user") openOr ""
  			def onlyCurrenunit = true//unit == ""
  			/*if(userId != ""){
  				val user = User.findByKey(userId.toLong).get
  				AuthUtil << user.unit.obj.get
  			}*/
  			if(unit != ""){
  				AuthUtil << CompanyUnit.findByKey(unit.toLong).get
  			}
	  		Script(
		  			OnLoad(
			  				Call("buildCalendar",
									JsArray(AuthUtil.company.usersForCalendar( onlyCurrenunit  ).map(
												u 	=> JsObj(
													("name",u.thumbAndName),
													("id",u.id.is),
													("group",u.group.is)
													//,("hours", JsArray(u.workHouers.map(h => JsObj(("day", h.day.is),("start", h.start.is),("end", h.end.is),("startLanch", h.startLanch.is),("endLanch", h.endLanch.is))) ))
												)
											)
									),
									JsArray(),
									AuthUtil.company.calendarInterval.is,
									AuthUtil.company.calendarIntervalAlt.is,
									AuthUtil.company.calendarStart.is,
									AuthUtil.company.calendarEnd.is,
									AuthUtil.company.autoIncrementCommand_?.is,
									AuthUtil.company.autoOpenCalendar_?.is,
									JsObj(
										("newEvent", AuthUtil.user.canCreateCalendarEvents_?.is),
										("deleteEvent", AuthUtil.user.canDeleteCalendarEvents_?.is),
										("moveEvent", AuthUtil.user.canMoveCalendarEvents_?.is),
										("editEvent", AuthUtil.user.canEditCalendarEvents_?.is)
									),
									JsObj(
										("useTreatmentClass", AuthUtil.company.useTreatmentAsAClass_?.is)
									),
									AuthUtil.unit.id.is,
									AuthUtil.company.calendarShowLight_?.is, // calendarShowLight
									AuthUtil.company.calendarShowInterval_?.is // calendarShowInterval
			  					)
			  			)
		  		)
  	}
}

