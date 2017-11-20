
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

class  MapIconSnippet extends BootstrapPaginatorSnippet[MapIcon] with SnippetUploadImage {

	def pageObj = MapIcon

	def findForListParamsWithoutOrder: List[QueryParam[MapIcon]] = 
	List(Like(MapIcon.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"))
	override def page = {
		if(!showAll){
			super.page
		}else{
			MapIcon.findAllInCompanyWithInactive(findForListParams :_*)
		}
	}

	def findForListParams: List[QueryParam[MapIcon]] = List(Like(MapIcon.search_name,"%"+BusinessRulesUtil.clearString(name)+"%"),OrderBy(MapIcon.name, Ascending), 
		StartAt(curPage*itemsPerPage), MaxRows(itemsPerPage))

	def list(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val ac = MapIcon.findByKey(id.toLong).get	
		  				ac.delete_!
		  				S.notice("Ícone excluído com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Ícone não existe!")
		  				case _ => S.error("Ícone não pode ser excluído!")
		  			}
			}

			page.flatMap(ac => 
			bind("f", xhtml,"name" -> Text(ac.name.is),
							"obs" -> Text(ac.obs.is),
							"iconpath" -> <img src={ac.iconPath}/>,
							"thumb" -> ac.thumb ("32"),
							"actions" -> <a class="btn" href={"/mapicon/mapicon?id="+ac.id.is}>Editar</a>,
							"delete" -> SHtml.submit("Excluir",delete,"class" -> "btn danger","data-confirm-message" -> {" excluir o ícone "+ac.name}),
							"_id" -> SHtml.text(ac.id.is.toString, id = _),
							"id" ->Text(ac.id.is.toString)
				)
			)
	}

	def getMapIcon:MapIcon = {
		def id = S.param("id") openOr "0"
		id match {
			case "0" => MapIcon.create
			case _ => MapIcon.findByKey(id.toLong).get
		}
	}

	def maintain = {
		try{
			var ac:MapIcon = getMapIcon
			def process(): JsCmd= {
				ac.company(AuthUtil.company)
				ac.save
			   	S.notice("Ícone salvo com sucesso!")
			   	S.redirectTo("/mapicon/mapicon?id="+ac.id.is)
			}
		    "name=name" #> (SHtml.text(ac.name.is, ac.name(_)))&
		    "name=short_name" #> (SHtml.text(ac.short_name.is, ac.short_name(_)))&
			"#img_mapicon" #> ac.thumb("48")&
			"name=obs" #> (SHtml.textarea(ac.obs.is, ac.obs(_)))&
			"name=status" #> (SHtml.select(status,Full(ac.status.is.toString),(v:String) => ac.status(v.toInt))++SHtml.hidden(process))			
		}catch {
		    case e: NoSuchElementException => S.error("Ícone não existe!")
		    "#MapIcon_form *" #> NodeSeq.Empty
  		}
  	}
	def setImageToEntity(homeName:String, thumbName:String){
		val mapicon = getMapIcon
		mapicon.image(homeName).imagethumb(thumbName).save
	}
  	def imageFolder:String = MapIcon.imagePath
  	def thumbToShow:NodeSeq = getMapIcon.thumb("48")
}

