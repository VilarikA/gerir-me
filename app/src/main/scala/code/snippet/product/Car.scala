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

object  CarSnippet{

	def cars(xhtml: NodeSeq): NodeSeq = {
			var id:String = ""
		 	def delete(): Unit ={
			  		try{
		  				val p = Car.findByKey(id.toLong).get	
		  				p.delete_!
		  				S.notice("Produto excluído com sucesso!")
		  			}catch{
		  				case e: NoSuchElementException => S.error("Produto não existe!")
		  				case _ => S.error("Produto não pode ser excluído!")
		  			}
			
			}

			<div>Todo</div>
	}

	def getCar:Car = {
		def id = S.param("id") openOr "0"
		id match {
			case "0" => Car.create
			case _ => Car.findByKey(id.toLong).get
		}
	}

	def maintain = {
		try{
			var p:Car = getCar
			def process(): JsCmd= {
				p.company(AuthUtil.company)
			   	p.save	
			   	S.notice("Salva com sucesso!")
			}
					
		    "name=name" #> (p.name.toForm)

		}catch {
		    case e: NoSuchElementException => S.error("Produto não existe!")
		    "#activity_form *" #> NodeSeq.Empty
  		}
  	}  	
}