package code
package snippet

import net.liftweb._
import http._
import code.util._
import net.liftweb.http.PaginatorSnippet
import scala.xml.{ NodeSeq, Text }
import net.liftweb.mapper._
import net.liftweb.common._
import net.liftweb.mapper.{StartAt, MaxRows}
import util.Helpers._
import code.model._;
import code.actors._;
import java.util.{Date, Calendar};

trait BootstrapPaginatorSnippet[T <: net.liftweb.mapper.LongKeyedMapper[T]] extends PaginatorSnippet[T]{
	val status = Seq(
	  				User.STATUS_OK.toString -> "Ativo" ,
	  				User.STATUS_INACTIVE.toString -> "Inativo"
	)

	def checkBooleanParamenter(name:String, defaultValue:Boolean = false) = {
		S.param(name) match {
			case Full(s) => !defaultValue
			case _ => defaultValue
		}
	}
	def showAll:Boolean = checkBooleanParamenter("all")		
	def pageObj: OnlyActive[T]

	def findForListParamsWithoutOrder: List[QueryParam[T]]

	def findForListParams: List[QueryParam[T]]

	override def itemsPerPage = 20 // rigel jun/2015 era 10 
  	override def pageXml(newFirst: Long, ns: NodeSeq): NodeSeq =
	    if(first==newFirst || newFirst < 0 || newFirst >= count)
	     <li class='disabled'><a>{ns}</a></li>//somento por causa do bootstrap
	    else
	    	if(S.get_?)
	      		<li><a href={pageUrlCurrent(newFirst)}>{ns}</a></li>
	      	else
	      		<li><a href={pageUrlCurrent(newFirst)}>{ns}</a></li>

	override lazy val count = {
		AuthUtil.company.count(pageObj,findForListParamsWithoutOrder)
	}
	override def page = AuthUtil.company.pagination(pageObj,findForListParams)

	def pageUrlCurrent(newFirst:Long) =  appendParams(S.uri, S.request.get.paramNames.filter(_ != "offset" ).map((a)=> a -> S.param(a).get) ::: List(offsetParam -> newFirst.toString))
	
	def name = S.param("name") match {
		case Full(s) => s
		case _ => ""
	}

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

	val sexs = Seq(
				Customer.Sexs.Female.toString -> "Feminino",
				Customer.Sexs.Male.toString -> "Masculino",
				Customer.Sexs.Undefined.toString -> "Não informado"
	)
}
